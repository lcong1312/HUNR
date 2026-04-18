<?php
declare(strict_types=1);
?>
<div class="bg-content auth-card">
    <div class="content">
<?php if ($flash !== null && ($flash['context'] ?? '') === 'login'): ?>
        <div class="auth-alert auth-alert--<?= e($flash['type']) ?>"><?= e($flash['message']) ?></div>
<?php endif; ?>
<?php if (!empty($loginError)): ?>
        <div class="auth-alert auth-alert--error"><?= e($loginError) ?></div>
<?php endif; ?>

        <div class="auth-shell">
<?php if ($authenticatedUser !== null): ?>
            <div class="auth-summary auth-panel">
                <div class="auth-summary__account">
                    <div>
                        <span>Tài khoản đang đăng nhập</span>
                        <strong><?= e((string) $authenticatedUser['username']) ?></strong>
                    </div>
                </div>

<?php if ($playerSummary !== null): ?>
                <div class="auth-summary__grid">
                    <div class="auth-summary__item">
                        <span class="auth-summary__label">Tên người chơi</span>
                        <strong class="auth-summary__value auth-summary__value--compact"><?= e($playerSummary['name']) ?></strong>
                    </div>
                    <div class="auth-summary__item">
                        <span class="auth-summary__label">Sức mạnh</span>
                        <strong class="auth-summary__value"><?= e(format_stat_number($playerSummary['power'])) ?></strong>
                    </div>
                    <div class="auth-summary__item">
                        <span class="auth-summary__label">Số tiền</span>
                        <strong class="auth-summary__value"><?= e(format_stat_number($playerSummary['money'])) ?></strong>
                    </div>
                </div>
                <p class="auth-summary__note">Dữ liệu hiển thị đang lấy trực tiếp từ bảng <strong>nr_player</strong> theo tài khoản hiện tại.</p>
<?php else: ?>
                <div class="auth-alert auth-alert--warning">Vui lòng tạo người chơi.</div>
<?php endif; ?>

                <div class="auth-form__actions">
                    <a class="auth-button auth-button--primary" href="<?= e(route_path('/nap-tien')) ?>">Nạp tiền</a>
                    <a class="auth-button auth-button--secondary" href="<?= e(route_path('/dang-xuat')) ?>">Đăng xuất</a>
                </div>
            </div>
<?php else: ?>
            <form method="post" class="auth-form auth-panel">
                <input type="hidden" name="csrf_token" value="<?= e(csrf_token()) ?>">
                <div class="auth-field">
                    <label for="login-username">Tài khoản</label>
                    <div class="auth-input">
                        <input id="login-username" type="text" name="username" maxlength="20" required placeholder="Nhập tài khoản đã đăng ký" value="<?= e($loginForm['username'] ?? '') ?>">
                    </div>
                </div>

                <div class="auth-field">
                    <label for="login-password">Mật khẩu</label>
                    <div class="auth-input">
                        <input id="login-password" type="password" name="password" maxlength="100" required placeholder="Nhập mật khẩu của bạn">
                    </div>
                </div>

                <div class="auth-form__actions">
                    <button class="auth-button auth-button--primary auth-button--wide" type="submit">Đăng nhập</button>
                </div>
            </form>

            <p class="auth-helper">
                Chưa có tài khoản? <a href="<?= e(route_path('/dang-ky')) ?>">Tạo tài khoản mới</a>
            </p>
<?php endif; ?>
        </div>
    </div>
</div>
        </div>
    </div>
    <br>
