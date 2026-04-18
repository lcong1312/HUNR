<?php
declare(strict_types=1);

$flash = isset($flash) && is_array($flash) ? $flash : null;
$topupError = isset($topupError) ? (string) $topupError : null;

$walletSummary = isset($walletSummary) && is_array($walletSummary)
    ? $walletSummary
    : [
        'id' => 0,
        'username' => '---',
        'coin' => 0,
        'tongnap' => 0,
    ];

$bankDetails = isset($bankDetails) && is_array($bankDetails) ? $bankDetails : [];
$paymentEnabled = isset($paymentEnabled) ? (bool) $paymentEnabled : false;
$paymentLimits = isset($paymentLimits) && is_array($paymentLimits)
    ? $paymentLimits
    : [
        'min_amount' => 1000,
        'max_amount' => 50000000,
    ];

$topupForm = isset($topupForm) && is_array($topupForm) ? $topupForm : ['amount' => ''];
$topupHistory = isset($topupHistory) && is_array($topupHistory) ? $topupHistory : [];
?>
<div class="bg-content auth-card">
    <div class="content">
<?php if ($flash !== null && ($flash['context'] ?? '') === 'topup'): ?>
        <div class="auth-alert auth-alert--<?= e($flash['type']) ?>"><?= e($flash['message']) ?></div>
<?php endif; ?>
<?php if (!empty($topupError)): ?>
        <div class="auth-alert auth-alert--error"><?= e($topupError) ?></div>
<?php endif; ?>

        <div class="auth-shell topup-shell">
            <div class="auth-summary auth-panel">
                <div class="auth-summary__account">
                    <div>
                        <span>Tài khoản đang sử dụng</span>
                        <strong><?= e((string) $walletSummary['username']) ?></strong>
                    </div>
                </div>

                <div class="auth-summary__grid topup-summary__grid">
                    <div class="auth-summary__item">
                        <span class="auth-summary__label">Số dư coin</span>
                        <strong class="auth-summary__value"><?= e(format_stat_number((int) $walletSummary['coin'])) ?></strong>
                    </div>
                    <div class="auth-summary__item">
                        <span class="auth-summary__label">Tổng đã nạp</span>
                        <strong class="auth-summary__value"><?= e(format_stat_number((int) $walletSummary['tongnap'])) ?></strong>
                    </div>
                </div>

                <p class="auth-summary__note">Coin sẽ được cộng vào `nr_user.coin`. Nếu nhân vật đang online, bạn nên thoát game vào lại để dữ liệu phiên đăng nhập được cập nhật.</p>
            </div>

            <div class="auth-panel topup-panel">
                <div class="topup-panel__heading">
                    <h3>Nạp tiền bằng ATM / QR</h3>
                </div>

                <div class="topup-bank">
                    <div class="topup-bank__item">
                        <span>Ngân hàng</span>
                        <strong><?= e((string) ($bankDetails['name'] ?? '')) ?></strong>
                    </div>
                    <div class="topup-bank__item">
                        <span>Chủ tài khoản</span>
                        <strong><?= e((string) ($bankDetails['account_name'] ?? '')) ?></strong>
                    </div>
                    <div class="topup-bank__item">
                        <span>Số tài khoản</span>
                        <strong><?= e((string) ($bankDetails['account_number'] ?? '')) ?></strong>
                    </div>
                </div>

<?php if ($paymentEnabled): ?>
                <form method="post" class="auth-form">
                    <input type="hidden" name="csrf_token" value="<?= e(csrf_token()) ?>">
                    <div class="auth-field">
                        <label for="topup-amount">Số tiền muốn nạp</label>
                        <div class="auth-input">
                            <input
                                id="topup-amount"
                                type="number"
                                name="amount"
                                min="<?= e((string) $paymentLimits['min_amount']) ?>"
                                max="<?= e((string) $paymentLimits['max_amount']) ?>"
                                step="1000"
                                required
                                placeholder="Nhập số tiền cần nạp"
                                value="<?= e((string) ($topupForm['amount'] ?? '')) ?>"
                            >
                        </div>
                        <div class="auth-field__hint">
                            Tối thiểu <?= e(format_stat_number((int) $paymentLimits['min_amount'])) ?> VNĐ, tối đa <?= e(format_stat_number((int) $paymentLimits['max_amount'])) ?> VNĐ.
                        </div>
                    </div>

                    <div class="auth-form__actions">
                        <button class="auth-button auth-button--primary" type="submit">Tạo liên kết thanh toán</button>
                        <a class="auth-button auth-button--secondary" href="<?= e(route_path('/dang-nhap')) ?>">Quay lại tài khoản</a>
                    </div>
                </form>
<?php else: ?>
                <div class="auth-alert auth-alert--warning">PayOS chưa được cấu hình đầy đủ nên chưa thể tạo liên kết nạp tiền.</div>
<?php endif; ?>
            </div>

            <div class="auth-panel topup-history">
                <div class="topup-panel__heading">
                    <h3>Lịch sử nạp gần đây</h3>
                    <p>Danh sách này lấy từ bảng log giao dịch của web hiện tại để tránh cộng tiền trùng khi PayOS trả về nhiều lần.</p>
                </div>

<?php if ($topupHistory !== []): ?>
                <div class="topup-history__table">
                    <table>
                        <thead>
                            <tr>
                                <th>Mã đơn</th>
                                <th>Số tiền</th>
                                <th>Trạng thái</th>
                                <th>Tạo lúc</th>
                            </tr>
                        </thead>
                        <tbody>
<?php foreach ($topupHistory as $historyItem): ?>
                            <tr>
                                <td>#<?= e((string) $historyItem['order_code']) ?></td>
                                <td><?= e(format_stat_number((int) $historyItem['amount'])) ?> VNĐ</td>
                                <td>
                                    <span class="topup-status topup-status--<?= e(payment_status_tone((string) $historyItem['status'])) ?>">
                                        <?= e(payment_status_label((string) $historyItem['status'])) ?>
                                    </span>
                                </td>
                                <td><?= e((string) $historyItem['created_at']) ?></td>
                            </tr>
<?php endforeach; ?>
                        </tbody>
                    </table>
                </div>
<?php else: ?>
                <p class="auth-summary__note">Chưa có giao dịch nạp ATM nào trên website này.</p>
<?php endif; ?>
            </div>
        </div>
    </div>
</div>
        </div>
    </div>
    <br>
