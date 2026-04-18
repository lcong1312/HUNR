<?php
declare(strict_types=1);
?>
<div class="bg-content auth-card">
    <div class="content">
<?php if ($flash !== null && ($flash['context'] ?? '') === 'register'): ?>
        <div class="auth-alert auth-alert--<?= e($flash['type']) ?>"><?= e($flash['message']) ?></div>
<?php endif; ?>
<?php if (!empty($registerError)): ?>
        <div class="auth-alert auth-alert--error"><?= e($registerError) ?></div>
<?php endif; ?>
        <div class="auth-shell">
            <form method="post" class="auth-form auth-panel">
                <input type="hidden" name="csrf_token" value="<?= e(csrf_token()) ?>">
                <div class="auth-field">
                    <label for="register-username">Tài khoản</label>
                    <div class="auth-input">
                        <input id="register-username" type="text" name="username" maxlength="20" required placeholder="Nhập tài khoản của bạn" value="<?= e($registerForm['username'] ?? '') ?>">
                    </div>
                    <div class="auth-field__hint">Tên đăng nhập tối đa 20 ký tự, chỉ dùng chữ, số và các ký tự . _ @</div>
                </div>

                <div class="auth-field">
                    <label for="register-password">Mật khẩu</label>
                    <div class="auth-input">
                        <input id="register-password" type="password" name="password" maxlength="100" required placeholder="Nhập mật khẩu đăng nhập">
                    </div>
                    <div class="auth-field__hint">Mật khẩu này được dùng trực tiếp để đăng nhập game và website.</div>
                </div>

                <div class="auth-form__actions">
                    <button class="auth-button auth-button--primary auth-button--wide" type="submit">Tạo tài khoản</button>
                </div>
            </form>

            <p class="auth-helper">
                Đã có tài khoản? <a href="<?= e(route_path('/dang-nhap')) ?>">Đăng nhập ngay</a>
            </p>
        </div>
    </div>
</div>
        </div>
    </div>
    <br>
