<?php
declare(strict_types=1);

$currentUser = auth_user();
?>
  <div class="snowEffect"></div>

  <div style="position: relative;" class="body_body">
    <a href="#" id="backTop"><img id="backTopimg" src="<?= e(asset('images/favicon-32x32.png')) ?>" alt="top"></a>

    <div class="div-12">
      <img height="12" src="<?= e(asset('images/18-1.png')) ?>" style="vertical-align: middle;" alt="18+">
      <span style="vertical-align: middle; color:#fff">Chơi quá 180 phút một ngày sẽ ảnh hưởng xấu đến sức khỏe.</span>
    </div>
    <div class="left_top"></div>
    <div class="bg_top">
      <div class="right_top"></div>
    </div>

    <div class="body-content">
      <div class="bg-content2">
        <h1 class="a">
          <a class="site-logo__link" href="<?= e(route_path('/')) ?>" title="Game bảy viên Chú Bé Rồng Online">
            <img class="site-logo__image" src="<?= e(asset('images/logo_chube_rong_tight.png')) ?>" alt="Game bảy viên Chú Bé Rồng Online">
          </a>
        </h1>
        <div id="top">
          <div class="link-more">
            <div class="h">
              <div class="bg_noel"></div>
              <div class="menu2">
                <table width="100%" cellspacing="4">
                  <tr class="menu">
                    <td<?= $page['id'] === 'trang-chu' ? " id='selected'" : '' ?>>
                      <a href="<?= e(route_path('/trang-chu')) ?>">Trang Chủ</a>
                    </td>
                    <td<?= $page['id'] === 'gioi-thieu' ? " id='selected'" : '' ?>>
                      <a href="<?= e(route_path('/gioi-thieu')) ?>">Giới Thiệu</a>
                    </td>
                    <td<?= $page['id'] === 'dien-dan' ? " id='selected'" : '' ?>>
                      <a href="<?= e(route_path('/dien-dan')) ?>" title="Diễn Đàn">Diễn Đàn</a>
                    </td>
                    <td>
                      <a href="https://www.facebook.com/CLONE.LEVIETCONG" target="_blank" rel="noopener noreferrer" title="Fanpage">Fanpage</a>
                    </td>
                  </tr>
                </table>
              </div>
              <div class="auth-shortcuts">
<?php if ($currentUser !== null): ?>
                <a class="auth-shortcuts__user auth-shortcuts__user--link" href="<?= e(route_path('/dang-nhap')) ?>">Tài khoản: <?= e((string) $currentUser['username']) ?></a>
                <a class="auth-shortcuts__link" href="<?= e(route_path('/dang-xuat')) ?>">Đăng xuất</a>
<?php else: ?>
                <a class="auth-shortcuts__link" href="<?= e(route_path('/dang-nhap')) ?>">Đăng nhập</a>
                <a class="auth-shortcuts__link" href="<?= e(route_path('/dang-ky')) ?>">Đăng ký</a>
<?php endif; ?>
              </div>
<?php if ($page['show_forum_banner']): ?>
              <div class="bg_top_22">
                <img src="<?= e(asset('images/4rum_280x90.png')) ?>" width="100%" alt="Diễn đàn Ngọc Rồng Online">
              </div>
<?php endif; ?>
            </div>
          </div>
        </div>
