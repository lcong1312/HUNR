<?php
declare(strict_types=1);

session_start();

$site = require __DIR__ . '/app/config/site.php';

function e(string $value): string
{
    return htmlspecialchars($value, ENT_QUOTES, 'UTF-8');
}

function app_base_path(): string
{
    $scriptName = str_replace('\\', '/', (string) ($_SERVER['SCRIPT_NAME'] ?? '/index.php'));
    $directory = str_replace('\\', '/', (string) dirname($scriptName));
    $directory = trim($directory, "/. \t\n\r\0\x0B");

    return $directory === '' ? '' : '/' . $directory;
}

function asset(string $path): string
{
    return route_path('/assets/' . ltrim($path, '/'));
}

function route_path(string $path = '/'): string
{
    $trimmed = trim($path, '/');
    $basePath = app_base_path();

    if ($trimmed === '') {
        return $basePath === '' ? '/' : $basePath . '/';
    }

    return ($basePath === '' ? '' : $basePath) . '/' . $trimmed;
}

function base_url(): string
{
    $scheme = 'http';

    if (!empty($_SERVER['HTTP_X_FORWARDED_PROTO'])) {
        $scheme = trim(explode(',', (string) $_SERVER['HTTP_X_FORWARDED_PROTO'])[0]);
    } elseif (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off') {
        $scheme = 'https';
    }

    $host = $_SERVER['HTTP_HOST'] ?? 'localhost';

    return $scheme . '://' . $host;
}

function site_url(string $path = ''): string
{
    if ($path === '') {
        return base_url() . route_path('/');
    }

    if (preg_match('~^https?://~i', $path) === 1) {
        return $path;
    }

    return base_url() . route_path($path);
}

function redirect_to(string $path): never
{
    header('Location: ' . site_url($path));
    exit;
}

function redirect_to_url(string $url): never
{
    header('Location: ' . $url);
    exit;
}

function csrf_token(): string
{
    if (empty($_SESSION['csrf_token'])) {
        $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
    }

    return (string) $_SESSION['csrf_token'];
}

function validate_csrf(?string $token): bool
{
    return is_string($token) && hash_equals(csrf_token(), $token);
}

function set_flash(string $type, string $message, string $context): void
{
    $_SESSION['flash'] = [
        'type' => $type,
        'message' => $message,
        'context' => $context,
    ];
}

function pull_flash(): ?array
{
    if (!isset($_SESSION['flash']) || !is_array($_SESSION['flash'])) {
        return null;
    }

    $flash = $_SESSION['flash'];
    unset($_SESSION['flash']);

    return $flash;
}

function auth_user(): ?array
{
    return isset($_SESSION['auth']) && is_array($_SESSION['auth']) ? $_SESSION['auth'] : null;
}

function login_user(array $account): void
{
    session_regenerate_id(true);
    $_SESSION['auth'] = [
        'id' => (int) $account['id'],
        'username' => (string) $account['username'],
    ];
}

function logout_user(): void
{
    unset($_SESSION['auth']);
}

function db_config(): array
{
    static $config;

    if ($config === null) {
    $config = require __DIR__ . '/app/config/database.php';
    }

    return $config;
}

function db(): PDO
{
    static $pdo;

    if ($pdo instanceof PDO) {
        return $pdo;
    }

    $config = db_config();
    $dsn = sprintf(
        'mysql:host=%s;port=%d;dbname=%s;charset=utf8mb4',
        $config['host'],
        $config['port'],
        $config['name']
    );

    $pdo = new PDO($dsn, $config['user'], $config['password'], [
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    ]);

    return $pdo;
}

function normalize_credential(?string $value): string
{
    return trim((string) $value);
}

function normalize_username(?string $value): string
{
    $username = normalize_credential($value);

    return function_exists('mb_strtolower')
        ? mb_strtolower($username, 'UTF-8')
        : strtolower($username);
}

function text_length(string $value): int
{
    return function_exists('mb_strlen') ? mb_strlen($value) : strlen($value);
}

function validate_credentials(string $username, string $password): ?string
{
    if ($username === '' || $password === '') {
        return 'Vui lòng nhập đầy đủ tài khoản và mật khẩu.';
    }

    if (preg_match('/^[a-zA-Z0-9._@]+$/', $username) !== 1) {
        return 'Tài khoản chỉ được chứa chữ, số và các ký tự . _ @';
    }

    if (text_length($username) > 20) {
        return 'Tài khoản tối đa 20 ký tự.';
    }

    if (text_length($password) > 100) {
        return 'Mật khẩu tối đa 100 ký tự.';
    }

    return null;
}

function register_account(string $username, string $password): ?string
{
    $pdo = db();

    $check = $pdo->prepare('SELECT id FROM nr_user WHERE username = ? LIMIT 1');
    $check->execute([$username]);

    if ($check->fetch() !== false) {
        return 'Tài khoản đã tồn tại.';
    }

    $insert = $pdo->prepare(
        'INSERT INTO nr_user(username, password, status, gold_bar, coin, lock_gold, lock_time, activated, role, create_time, ip, domain)
         VALUES (?, ?, 0, 0, 0, NULL, NULL, 0, 0, NOW(), ?, ?)'
    );
    $insert->execute([
        $username,
        $password,
        (string) ($_SERVER['REMOTE_ADDR'] ?? 'none'),
        (string) ($_SERVER['HTTP_HOST'] ?? 'none'),
    ]);

    return null;
}

function attempt_login(string $username, string $password): ?array
{
    $pdo = db();
    $stmt = $pdo->prepare('SELECT id, username, status, lock_time FROM nr_user WHERE username = ? AND password = ? LIMIT 1');
    $stmt->execute([$username, $password]);
    $account = $stmt->fetch();

    if ($account === false) {
        return null;
    }

    if ((int) $account['status'] === 1) {
        throw new RuntimeException('Tài khoản đã bị khóa.');
    }

    if (!empty($account['lock_time'])) {
        throw new RuntimeException('Tài khoản đang bị khóa tạm thời.');
    }

    return $account;
}

function first_numeric_string(mixed $value): string
{
    if (is_int($value) || is_float($value) || is_string($value)) {
        $normalized = trim((string) $value);

        if ($normalized !== '' && preg_match('/^-?\d+$/', $normalized) === 1) {
            return $normalized;
        }
    }

    return '0';
}

function player_summary(int $accountId): ?array
{
    $pdo = db();
    $stmt = $pdo->prepare(
        "SELECT
            name,
            COALESCE(CAST(JSON_UNQUOTE(JSON_EXTRACT(info, '$.power')) AS UNSIGNED), 0) AS power,
            head,
            gender,
            gold
         FROM nr_player
         WHERE user_id = ?
         ORDER BY id ASC
         LIMIT 1"
    );
    $stmt->execute([$accountId]);
    $player = $stmt->fetch();

    if ($player === false) {
        return null;
    }

    return [
        'name' => (string) $player['name'],
        'power' => first_numeric_string($player['power']),
        'money' => first_numeric_string($player['gold']),
        'head' => (int) $player['head'],
        'gender' => (int) $player['gender'],
    ];
}

function player_forum_profile(int $accountId): ?array
{
    $player = player_summary($accountId);

    if ($player === null) {
        return null;
    }

    return [
        'name' => (string) $player['name'],
        'head' => (int) $player['head'],
        'gender' => (int) $player['gender'],
    ];
}

function ensure_forum_posts_table(): void
{
    $pdo = db();
    $pdo->exec(
        <<<'SQL'
CREATE TABLE IF NOT EXISTS web_forum_posts (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content MEDIUMTEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
SQL
    );
}

function forum_post_excerpt(string $content, int $limit = 140): string
{
    $normalized = trim((string) preg_replace('/\s+/', ' ', $content));

    if ($normalized === '') {
        return '';
    }

    if (text_length($normalized) <= $limit) {
        return $normalized;
    }

    $excerptLength = max(1, $limit - 3);
    $excerpt = function_exists('mb_substr')
        ? mb_substr($normalized, 0, $excerptLength)
        : substr($normalized, 0, $excerptLength);

    return rtrim((string) $excerpt) . '...';
}

function player_avatar_asset(int $head, int $gender): string
{
    $genderImage = [
        0 => 'images/516.png',
        1 => 'images/523.png',
        2 => 'images/520.png',
    ];

    return asset($genderImage[$gender] ?? 'images/516.png');
}

function validate_forum_feedback(string $content): ?string
{
    if ($content === '') {
        return 'Vui lòng nhập nội dung góp ý.';
    }

    if (text_length($content) < 6) {
        return 'Nội dung góp ý tối thiểu 6 ký tự.';
    }

    if (text_length($content) > 1200) {
        return 'Nội dung góp ý tối đa 1200 ký tự.';
    }

    return null;
}

function ensure_forum_feedback_table(): void
{
    $pdo = db();
    $pdo->exec(
        <<<'SQL'
CREATE TABLE IF NOT EXISTS web_forum_feedback (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    player_name VARCHAR(100) NOT NULL,
    player_head INT NOT NULL,
    player_gender INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_created_at (created_at),
    KEY idx_account_id (account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
SQL
    );
}

function forum_create_feedback(array $account, array $player, string $content): void
{
    ensure_forum_feedback_table();

    $pdo = db();
    $stmt = $pdo->prepare(
        'INSERT INTO web_forum_feedback(account_id, username, player_name, player_head, player_gender, content) VALUES (?, ?, ?, ?, ?, ?)'
    );
    $stmt->execute([
        (int) $account['id'],
        (string) $account['username'],
        (string) $player['name'],
        (int) $player['head'],
        (int) $player['gender'],
        $content,
    ]);
}

function forum_recent_feedback(int $limit = 15): array
{
    ensure_forum_feedback_table();

    $pdo = db();
    $stmt = $pdo->prepare(
        'SELECT username, player_name, player_head, player_gender, content, created_at FROM web_forum_feedback ORDER BY id DESC LIMIT ?'
    );
    $stmt->bindValue(1, max(1, min(100, $limit)), PDO::PARAM_INT);
    $stmt->execute();

    $items = $stmt->fetchAll();

    if (!is_array($items)) {
        return [];
    }

    foreach ($items as &$item) {
        $item['avatar_asset'] = player_avatar_asset((int) $item['player_head'], (int) $item['player_gender']);
    }
    unset($item);

    return $items;
}

function format_relative_time(?string $dateTime): string
{
    if ($dateTime === null || trim($dateTime) === '') {
        return 'Vừa xong';
    }

    try {
        $createdAt = new DateTimeImmutable($dateTime);
        $now = new DateTimeImmutable('now', $createdAt->getTimezone());
    } catch (Throwable $exception) {
        return $dateTime;
    }

    $diff = max(0, $now->getTimestamp() - $createdAt->getTimestamp());

    if ($diff < 60) {
        return 'Vừa xong';
    }

    if ($diff < 3600) {
        return (string) floor($diff / 60) . ' phút trước';
    }

    if ($diff < 86400) {
        return (string) floor($diff / 3600) . ' giờ trước';
    }

    if ($diff < 604800) {
        return (string) floor($diff / 86400) . ' ngày trước';
    }

    if ($diff < 2592000) {
        return (string) floor($diff / 604800) . ' tuần trước';
    }

    if ($diff < 31536000) {
        return (string) floor($diff / 2592000) . ' tháng trước';
    }

    return (string) floor($diff / 31536000) . ' năm trước';
}

function forum_render_content(string $content): string
{
    $escaped = e($content);
    $linked = preg_replace(
        '~(https?://[^\s<]+)~i',
        '<a href="$1" target="_blank" rel="noopener noreferrer">$1</a>',
        $escaped
    );

    return nl2br($linked ?? $escaped);
}

function forum_recent_posts(int $limit = 12): array
{
    ensure_forum_posts_table();

    $pdo = db();
    $stmt = $pdo->prepare(
        'SELECT id, title, content, created_at, updated_at FROM web_forum_posts ORDER BY created_at DESC, id DESC LIMIT ?'
    );
    $stmt->bindValue(1, max(1, min(100, $limit)), PDO::PARAM_INT);
    $stmt->execute();

    $items = $stmt->fetchAll();

    if (!is_array($items)) {
        return [];
    }

    foreach ($items as &$item) {
        $item['excerpt'] = forum_post_excerpt((string) $item['content']);
        $item['relative_created_at'] = format_relative_time($item['created_at'] ?? null);
    }
    unset($item);

    return $items;
}

function forum_find_post(int $postId): ?array
{
    ensure_forum_posts_table();

    $pdo = db();
    $stmt = $pdo->prepare(
        'SELECT id, title, content, created_at, updated_at FROM web_forum_posts WHERE id = ? LIMIT 1'
    );
    $stmt->execute([$postId]);
    $post = $stmt->fetch();

    if ($post === false) {
        return null;
    }

    $post['excerpt'] = forum_post_excerpt((string) $post['content']);
    $post['relative_created_at'] = format_relative_time($post['created_at'] ?? null);

    return $post;
}

function format_stat_number(int|float|string $value): string
{
    $number = trim((string) $value);

    if ($number === '') {
        return '0';
    }

    $sign = '';

    if (str_starts_with($number, '-')) {
        $sign = '-';
        $number = substr($number, 1);
    }

    if ($number === '' || preg_match('/^\d+$/', $number) !== 1) {
        return $sign . $number;
    }

    $number = ltrim($number, '0');

    if ($number === '') {
        return '0';
    }

    return $sign . preg_replace('/\B(?=(\d{3})+(?!\d))/', '.', $number);
}

require __DIR__ . '/app/support/topup.php';

$requestPath = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH) ?: '/';
$requestPath = rawurldecode($requestPath);
$basePath = app_base_path();

if ($basePath !== '' && ($requestPath === $basePath || str_starts_with($requestPath, $basePath . '/'))) {
    $requestPath = substr($requestPath, strlen($basePath));
    $requestPath = $requestPath === '' ? '/' : $requestPath;
}

$requestPath = '/' . trim($requestPath, '/');

if ($requestPath === '/index.php' || $requestPath === '//') {
    $requestPath = '/';
}

if ($requestPath !== '/' && str_ends_with($requestPath, '/')) {
    $requestPath = rtrim($requestPath, '/');
}

$flash = pull_flash();
$registerError = null;
$loginError = null;
$topupError = null;
$forumError = null;
$registerForm = ['username' => ''];
$loginForm = ['username' => ''];
$topupForm = ['amount' => ''];
$bankDetails = payment_bank_details();
$paymentLimits = payment_limits();
$paymentEnabled = payment_is_configured();
$walletSummary = null;
$topupHistory = [];
$forumPosts = [];
$forumCurrentPost = null;
$forumRequestedPostId = null;
$authenticatedUser = auth_user();

if ($requestPath === '/dang-xuat') {
    logout_user();
    set_flash('success', 'Đã đăng xuất thành công.', 'login');
    redirect_to('/dang-nhap');
}

if ($requestPath === '/nap-tien/ket-qua') {
    try {
        $result = handle_payment_return($_GET['orderCode'] ?? null);
    } catch (Throwable $exception) {
        $result = [
            'type' => 'error',
            'message' => $exception->getMessage() !== '' ? $exception->getMessage() : 'Không thể xác minh giao dịch nạp tiền.',
        ];
    }

    if ($authenticatedUser !== null) {
        set_flash($result['type'], $result['message'], 'topup');
        redirect_to('/nap-tien');
    }

    set_flash($result['type'], $result['message'], 'login');
    redirect_to('/dang-nhap');
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (!validate_csrf($_POST['csrf_token'] ?? null)) {
        $message = 'Phiên làm việc đã hết hạn. Vui lòng thử lại.';

        if ($requestPath === '/dang-ky') {
            $registerError = $message;
        } elseif ($requestPath === '/dang-nhap') {
            $loginError = $message;
        } elseif ($requestPath === '/nap-tien') {
            $topupError = $message;
        } elseif ($requestPath === '/dien-dan') {
            $forumError = $message;
        }
    } elseif ($requestPath === '/dang-ky') {
        $registerForm['username'] = normalize_username($_POST['username'] ?? null);
        $password = normalize_credential($_POST['password'] ?? null);
        $validationError = validate_credentials($registerForm['username'], $password);

        if ($validationError !== null) {
            $registerError = $validationError;
        } else {
            try {
                $registerError = register_account($registerForm['username'], $password);

                if ($registerError === null) {
                    set_flash('success', 'Đăng ký thành công. Vui lòng đăng nhập.', 'login');
                    redirect_to('/dang-nhap');
                }
            } catch (Throwable $exception) {
                $registerError = 'Không thể tạo tài khoản lúc này.';
            }
        }
    } elseif ($requestPath === '/dang-nhap') {
        $loginForm['username'] = normalize_username($_POST['username'] ?? null);
        $password = normalize_credential($_POST['password'] ?? null);
        $validationError = validate_credentials($loginForm['username'], $password);

        if ($validationError !== null) {
            $loginError = $validationError;
        } else {
            try {
                $account = attempt_login($loginForm['username'], $password);

                if ($account === null) {
                    $loginError = 'Thông tin tài khoản hoặc mật khẩu không chính xác.';
                } else {
                    login_user($account);
                    set_flash('success', 'Đăng nhập thành công.', 'login');
                    redirect_to('/dang-nhap');
                }
            } catch (Throwable $exception) {
                $loginError = $exception->getMessage() !== '' ? $exception->getMessage() : 'Đăng nhập thất bại.';
            }
        }
    } elseif ($requestPath === '/nap-tien') {
        if ($authenticatedUser === null) {
            set_flash('error', 'Vui lòng đăng nhập để sử dụng chức năng nạp tiền.', 'login');
            redirect_to('/dang-nhap');
        }

        $topupForm['amount'] = trim((string) ($_POST['amount'] ?? ''));
        $amount = payment_amount_from_input($_POST['amount'] ?? null);
        $validationError = validate_payment_amount($amount);

        if (!$paymentEnabled) {
            $topupError = 'Chức năng nạp tiền đang tạm bảo trì.';
        } elseif ($validationError !== null) {
            $topupError = $validationError;
        } else {
            try {
                $paymentLink = create_payment_link($authenticatedUser, $amount);
                redirect_to_url((string) $paymentLink['checkoutUrl']);
            } catch (Throwable $exception) {
                $topupError = $exception->getMessage() !== '' ? $exception->getMessage() : 'Không thể tạo liên kết thanh toán lúc này.';
            }
        }
    } elseif ($requestPath === '/dien-dan/legacy-feedback') {
        if ($authenticatedUser === null) {
            set_flash('error', 'Vui lòng đăng nhập để vào diễn đàn góp ý.', 'login');
            redirect_to('/dang-nhap');
        }

        try {
            $forumPlayer = player_forum_profile((int) $authenticatedUser['id']);
        } catch (Throwable $exception) {
            $forumPlayer = null;
        }

        if ($forumPlayer === null) {
            set_flash('warning', 'Bạn cần tạo nhân vật trước khi gửi góp ý trên diễn đàn.', 'login');
            redirect_to('/dang-nhap');
        }

        $forumForm['content'] = trim((string) ($_POST['content'] ?? ''));
        $validationError = validate_forum_feedback($forumForm['content']);

        if ($validationError !== null) {
            $forumError = $validationError;
        } else {
            try {
                forum_create_feedback($authenticatedUser, $forumPlayer, $forumForm['content']);
                set_flash('success', 'Đã gửi góp ý thành công. Cảm ơn bạn!', 'forum');
                redirect_to('/dien-dan');
            } catch (Throwable $exception) {
                $forumError = 'Không thể lưu góp ý lúc này. Vui lòng thử lại sau.';
            }
        }
    }
}

$authenticatedUser = auth_user();
$playerSummary = null;

if ($requestPath === '/dang-nhap' && $authenticatedUser !== null) {
    try {
        $playerSummary = player_summary((int) $authenticatedUser['id']);
    } catch (Throwable $exception) {
        $loginError = 'Không thể lấy dữ liệu nhân vật từ cơ sở dữ liệu.';
    }
}

if ($requestPath === '/nap-tien') {
    if ($authenticatedUser === null) {
        set_flash('error', 'Vui lòng đăng nhập để sử dụng chức năng nạp tiền.', 'login');
        redirect_to('/dang-nhap');
    }

    $walletSummary = [
        'id' => (int) $authenticatedUser['id'],
        'username' => (string) $authenticatedUser['username'],
        'coin' => 0,
        'tongnap' => 0,
    ];

    try {
        $summary = payment_wallet_summary((int) $authenticatedUser['id']);

        if ($summary !== null) {
            $walletSummary = $summary;
        } elseif ($topupError === null) {
            $topupError = 'Không thể tìm thấy thông tin tài khoản để nạp tiền.';
        }

        $topupHistory = payment_history((int) $authenticatedUser['id']);
    } catch (Throwable $exception) {
        if ($topupError === null) {
            $topupError = 'Không thể tải dữ liệu nạp tiền từ cơ sở dữ liệu.';
        }
    }
}

if ($requestPath === '/dien-dan/legacy-feedback') {
    if ($authenticatedUser === null) {
        set_flash('error', 'Vui lòng đăng nhập để vào diễn đàn góp ý.', 'login');
        redirect_to('/dang-nhap');
    }

    try {
        if ($forumPlayer === null) {
            $forumPlayer = player_forum_profile((int) $authenticatedUser['id']);
        }

        if ($forumPlayer === null) {
            set_flash('warning', 'Bạn cần tạo nhân vật trước khi vào diễn đàn góp ý.', 'login');
            redirect_to('/dang-nhap');
        }

        $forumPosts = forum_recent_feedback(20);
    } catch (Throwable $exception) {
        if ($forumError === null) {
            $forumError = 'Không thể tải dữ liệu diễn đàn từ cơ sở dữ liệu.';
        }
    }
}

if ($requestPath === '/dien-dan') {
    $requestedPost = trim((string) ($_GET['post'] ?? ''));

    if ($requestedPost !== '' && preg_match('/^\d+$/', $requestedPost) === 1) {
        $forumRequestedPostId = (int) $requestedPost;
    } elseif ($requestedPost !== '') {
        $forumError = 'Bài viết không hợp lệ.';
        http_response_code(404);
    }

    try {
        $forumPosts = forum_recent_posts(20);

        if ($forumRequestedPostId !== null) {
            $forumCurrentPost = forum_find_post($forumRequestedPostId);

            if ($forumCurrentPost === null) {
                $forumError = 'Bài viết không tồn tại hoặc đã bị xoá.';
                http_response_code(404);
            }
        }
    } catch (Throwable $exception) {
        if ($forumError === null) {
            $forumError = 'Không thể tải dữ liệu diễn đàn từ cơ sở dữ liệu.';
        }
    }
}

$page = $site['pages'][$requestPath] ?? $site['not_found'];

if ($requestPath === '/dien-dan' && $forumCurrentPost !== null) {
    $page['title'] = (string) $forumCurrentPost['title'] . ' - Ngọc Rồng Online';
    $page['canonical'] = '/dien-dan?post=' . (string) $forumCurrentPost['id'];
}

if (!isset($site['pages'][$requestPath])) {
    http_response_code(404);
}

require __DIR__ . '/app/views/layouts/app.php';
