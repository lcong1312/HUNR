<?php
declare(strict_types=1);

function payment_config(): array
{
    static $config;

    if ($config === null) {
        $config = require __DIR__ . '/../config/payment.php';
    }

    return $config;
}

function payment_is_configured(): bool
{
    $payos = payment_config()['payos'] ?? [];

    return ($payos['client_id'] ?? '') !== ''
        && ($payos['api_key'] ?? '') !== ''
        && ($payos['checksum_key'] ?? '') !== '';
}

function payment_bank_details(): array
{
    return payment_config()['bank'] ?? [];
}

function payment_limits(): array
{
    $limits = payment_config()['limits'] ?? [];

    return [
        'min_amount' => max(1000, (int) ($limits['min_amount'] ?? 1000)),
        'max_amount' => max(1000, (int) ($limits['max_amount'] ?? 50000000)),
    ];
}

function payment_table_name(): string
{
    return (string) (payment_config()['storage']['table'] ?? 'bank_topup_orders');
}

function ensure_payment_tables(): void
{
    static $ready = false;

    if ($ready) {
        return;
    }

    $table = payment_table_name();
    $sql = <<<SQL
CREATE TABLE IF NOT EXISTS `$table` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `account_id` int NOT NULL,
    `username` varchar(20) NOT NULL,
    `order_code` bigint NOT NULL,
    `payment_link_id` varchar(64) DEFAULT NULL,
    `amount` int NOT NULL,
    `amount_paid` int NOT NULL DEFAULT 0,
    `description` varchar(255) NOT NULL,
    `checkout_url` text DEFAULT NULL,
    `qr_code` mediumtext DEFAULT NULL,
    `status` varchar(32) NOT NULL DEFAULT 'PENDING',
    `raw_request` longtext DEFAULT NULL,
    `raw_response` longtext DEFAULT NULL,
    `paid_at` datetime DEFAULT NULL,
    `credited_at` datetime DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_order_code` (`order_code`),
    KEY `idx_account_created` (`account_id`, `created_at`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
SQL;

    db()->exec($sql);
    $ready = true;
}

function payment_wallet_summary(int $accountId): ?array
{
    ensure_payment_tables();

    $stmt = db()->prepare('SELECT id, username, coin FROM nr_user WHERE id = ? LIMIT 1');
    $stmt->execute([$accountId]);
    $row = $stmt->fetch();

    if ($row === false) {
        return null;
    }

    $table = payment_table_name();
    $historyStmt = db()->prepare(
        "SELECT COALESCE(SUM(amount), 0) AS total_paid
         FROM `$table`
         WHERE account_id = ? AND credited_at IS NOT NULL"
    );
    $historyStmt->execute([$accountId]);
    $history = $historyStmt->fetch();

    return [
        'id' => (int) $row['id'],
        'username' => (string) $row['username'],
        'coin' => (int) $row['coin'],
        'tongnap' => (int) ($history['total_paid'] ?? 0),
    ];
}

function payment_history(int $accountId, int $limit = 8): array
{
    ensure_payment_tables();

    $limit = max(1, min(20, $limit));
    $table = payment_table_name();
    $stmt = db()->prepare(
        "SELECT order_code, amount, amount_paid, status, created_at, paid_at, credited_at
         FROM `$table`
         WHERE account_id = ?
         ORDER BY id DESC
         LIMIT $limit"
    );
    $stmt->execute([$accountId]);

    $rows = $stmt->fetchAll();

    return is_array($rows) ? $rows : [];
}

function payment_amount_from_input(mixed $value): int
{
    $normalized = preg_replace('/[^\d]/', '', trim((string) $value));

    if (!is_string($normalized) || $normalized === '') {
        return 0;
    }

    return (int) $normalized;
}

function validate_payment_amount(int $amount): ?string
{
    $limits = payment_limits();

    if ($amount <= 0) {
        return 'Vui lòng nhập số tiền hợp lệ.';
    }

    if ($amount < $limits['min_amount']) {
        return 'Số tiền nạp tối thiểu là ' . format_stat_number($limits['min_amount']) . ' VNĐ.';
    }

    if ($amount > $limits['max_amount']) {
        return 'Số tiền nạp tối đa là ' . format_stat_number($limits['max_amount']) . ' VNĐ.';
    }

    return null;
}

function payment_status_label(string $status): string
{
    return match (strtoupper(trim($status))) {
        'PAID' => 'Đã thanh toán',
        'PENDING' => 'Chờ thanh toán',
        'PROCESSING' => 'Đang xử lý',
        'CANCELLED' => 'Đã hủy',
        'EXPIRED' => 'Hết hạn',
        'FAILED' => 'Thất bại',
        default => 'Không xác định',
    };
}

function payment_status_tone(string $status): string
{
    return match (strtoupper(trim($status))) {
        'PAID' => 'success',
        'PENDING', 'PROCESSING' => 'warning',
        'CANCELLED', 'EXPIRED', 'FAILED' => 'error',
        default => 'neutral',
    };
}

function payment_order_exists(int $orderCode): bool
{
    $table = payment_table_name();
    $stmt = db()->prepare("SELECT id FROM `$table` WHERE order_code = ? LIMIT 1");
    $stmt->execute([$orderCode]);

    return $stmt->fetch() !== false;
}

function create_payment_order_code(): int
{
    ensure_payment_tables();

    do {
        $orderCode = (int) (date('ymdHis') . random_int(10, 99));
    } while (payment_order_exists($orderCode));

    return $orderCode;
}

function payment_description_for_user(string $username): string
{
    $description = 'NAP ' . trim($username);

    if (function_exists('mb_substr')) {
        return mb_substr($description, 0, 25);
    }

    return substr($description, 0, 25);
}

function payment_encode_json(mixed $value): string
{
    $json = json_encode($value, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

    return is_string($json) ? $json : '{}';
}

function payos_signature_from_object(array $data): string
{
    $checksumKey = (string) (payment_config()['payos']['checksum_key'] ?? '');

    ksort($data);
    $parts = [];

    foreach ($data as $key => $value) {
        if ($value === 'undefined' || $value === 'null' || $value === null) {
            $value = '';
        }

        if (is_array($value)) {
            $value = array_map(static function ($item) {
                if (is_array($item)) {
                    ksort($item);
                }

                return $item;
            }, $value);
            $value = json_encode($value, JSON_UNESCAPED_UNICODE);
        }

        $parts[] = $key . '=' . $value;
    }

    return hash_hmac('sha256', implode('&', $parts), $checksumKey);
}

function payos_payment_request_signature(array $data): string
{
    $checksumKey = (string) (payment_config()['payos']['checksum_key'] ?? '');
    $raw = sprintf(
        'amount=%d&cancelUrl=%s&description=%s&orderCode=%d&returnUrl=%s',
        (int) $data['amount'],
        (string) $data['cancelUrl'],
        (string) $data['description'],
        (int) $data['orderCode'],
        (string) $data['returnUrl']
    );

    return hash_hmac('sha256', $raw, $checksumKey);
}

function payos_request(string $method, string $path, ?array $payload = null): array
{
    if (!payment_is_configured()) {
        throw new RuntimeException('Chức năng nạp tiền chưa được cấu hình PayOS.');
    }

    if (!function_exists('curl_init')) {
        throw new RuntimeException('Máy chủ chưa bật extension cURL.');
    }

    $payos = payment_config()['payos'];
    $url = rtrim((string) $payos['base_url'], '/') . '/' . ltrim($path, '/');

    $curl = curl_init($url);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_CUSTOMREQUEST, strtoupper($method));
    curl_setopt($curl, CURLOPT_HTTPHEADER, [
        'x-client-id: ' . (string) $payos['client_id'],
        'x-api-key: ' . (string) $payos['api_key'],
        'Content-Type: application/json',
    ]);

    if ($payload !== null) {
        curl_setopt($curl, CURLOPT_POSTFIELDS, payment_encode_json($payload));
    }

    $body = curl_exec($curl);

    if ($body === false) {
        $error = curl_error($curl);
        curl_close($curl);
        throw new RuntimeException($error !== '' ? $error : 'Không thể kết nối PayOS.');
    }

    $httpCode = (int) curl_getinfo($curl, CURLINFO_HTTP_CODE);
    curl_close($curl);

    $decoded = json_decode($body, true);

    if (!is_array($decoded)) {
        throw new RuntimeException('PayOS trả về dữ liệu không hợp lệ.');
    }

    if (($decoded['code'] ?? '') !== '00') {
        $message = (string) ($decoded['desc'] ?? 'Yêu cầu PayOS thất bại.');
        throw new RuntimeException($message !== '' ? $message : 'Yêu cầu PayOS thất bại.');
    }

    if ($httpCode >= 400) {
        throw new RuntimeException('PayOS từ chối yêu cầu với mã HTTP ' . $httpCode . '.');
    }

    if (!isset($decoded['data']) || !is_array($decoded['data'])) {
        throw new RuntimeException('PayOS không trả về dữ liệu giao dịch.');
    }

    if (!isset($decoded['signature']) || !is_string($decoded['signature'])) {
        throw new RuntimeException('Thiếu chữ ký xác minh từ PayOS.');
    }

    $expectedSignature = payos_signature_from_object($decoded['data']);

    if (!hash_equals($expectedSignature, $decoded['signature'])) {
        throw new RuntimeException('Dữ liệu PayOS trả về không hợp lệ.');
    }

    return $decoded['data'];
}

function create_payment_link(array $account, int $amount): array
{
    ensure_payment_tables();

    $orderCode = create_payment_order_code();
    $callbackUrl = site_url('/nap-tien/ket-qua');
    $request = [
        'orderCode' => $orderCode,
        'amount' => $amount,
        'description' => payment_description_for_user((string) $account['username']),
        'returnUrl' => $callbackUrl,
        'cancelUrl' => $callbackUrl,
        'items' => [
            [
                'name' => 'Nạp tiền ATM',
                'quantity' => 1,
                'price' => $amount,
            ],
        ],
    ];

    $response = payos_request('POST', '/v2/payment-requests', array_merge(
        $request,
        ['signature' => payos_payment_request_signature($request)]
    ));

    if (($response['checkoutUrl'] ?? '') === '') {
        throw new RuntimeException('PayOS không trả về liên kết thanh toán.');
    }

    $table = payment_table_name();
    $stmt = db()->prepare(
        "INSERT INTO `$table`
            (account_id, username, order_code, payment_link_id, amount, description, checkout_url, qr_code, status, raw_request, raw_response)
         VALUES
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    );
    $stmt->execute([
        (int) $account['id'],
        (string) $account['username'],
        $orderCode,
        (string) ($response['paymentLinkId'] ?? ''),
        $amount,
        (string) $request['description'],
        (string) ($response['checkoutUrl'] ?? ''),
        (string) ($response['qrCode'] ?? ''),
        strtoupper((string) ($response['status'] ?? 'PENDING')),
        payment_encode_json($request),
        payment_encode_json($response),
    ]);

    return [
        'orderCode' => $orderCode,
        'checkoutUrl' => (string) ($response['checkoutUrl'] ?? ''),
        'qrCode' => (string) ($response['qrCode'] ?? ''),
    ];
}

function payment_link_information(int $orderCode): array
{
    return payos_request('GET', '/v2/payment-requests/' . $orderCode);
}

function handle_payment_return(mixed $orderCodeValue): array
{
    $orderCode = payment_amount_from_input($orderCodeValue);

    if ($orderCode <= 0) {
        return [
            'type' => 'warning',
            'message' => 'Không tìm thấy mã đơn nạp cần xác nhận.',
        ];
    }

    ensure_payment_tables();

    $paymentData = payment_link_information($orderCode);
    $status = strtoupper((string) ($paymentData['status'] ?? 'PENDING'));
    $amountPaid = (int) ($paymentData['amountPaid'] ?? 0);
    $paymentLinkId = (string) ($paymentData['id'] ?? '');
    $transactions = $paymentData['transactions'] ?? [];
    $paidAt = null;

    if (is_array($transactions) && isset($transactions[0]['transactionDateTime'])) {
        $paidAt = (string) $transactions[0]['transactionDateTime'];
    }

    $table = payment_table_name();
    $pdo = db();
    $pdo->beginTransaction();

    try {
        $stmt = $pdo->prepare("SELECT * FROM `$table` WHERE order_code = ? LIMIT 1 FOR UPDATE");
        $stmt->execute([$orderCode]);
        $record = $stmt->fetch();

        if ($record === false) {
            throw new RuntimeException('Đơn nạp không tồn tại trên hệ thống.');
        }

        $update = $pdo->prepare(
            "UPDATE `$table`
             SET payment_link_id = ?, status = ?, amount_paid = ?, paid_at = ?, raw_response = ?
             WHERE id = ?"
        );
        $update->execute([
            $paymentLinkId,
            $status,
            $amountPaid,
            $paidAt,
            payment_encode_json($paymentData),
            (int) $record['id'],
        ]);

        if ($record['credited_at'] !== null) {
            $pdo->commit();

            return [
                'type' => 'success',
                'message' => 'Đơn nạp này đã được xác nhận trước đó.',
            ];
        }

        if ($status !== 'PAID') {
            $pdo->commit();

            return [
                'type' => 'warning',
                'message' => match ($status) {
                    'CANCELLED' => 'Bạn đã hủy giao dịch nạp tiền.',
                    'EXPIRED' => 'Liên kết thanh toán đã hết hạn.',
                    default => 'Giao dịch chưa hoàn tất. Trạng thái hiện tại: ' . payment_status_label($status) . '.',
                },
            ];
        }

        $expectedAmount = (int) $record['amount'];

        if ($amountPaid < $expectedAmount) {
            $pdo->commit();

            return [
                'type' => 'warning',
                'message' => 'Giao dịch chưa thanh toán đủ số tiền yêu cầu.',
            ];
        }

        $accountStmt = $pdo->prepare('UPDATE nr_user SET coin = COALESCE(coin, 0) + ? WHERE id = ?');
        $accountStmt->execute([$expectedAmount, (int) $record['account_id']]);

        if ($accountStmt->rowCount() !== 1) {
            throw new RuntimeException('Không thể cộng tiền vào tài khoản.');
        }

        $creditStmt = $pdo->prepare("UPDATE `$table` SET credited_at = NOW() WHERE id = ?");
        $creditStmt->execute([(int) $record['id']]);

        $pdo->commit();

        return [
            'type' => 'success',
            'message' => 'Nạp tiền thành công ' . format_stat_number($expectedAmount) . ' coin vào tài khoản ' . (string) $record['username'] . '.',
        ];
    } catch (Throwable $exception) {
        if ($pdo->inTransaction()) {
            $pdo->rollBack();
        }

        throw $exception;
    }
}
