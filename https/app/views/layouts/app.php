<?php
declare(strict_types=1);

require __DIR__ . '/../partials/head.php';
?>
<body class="" style="overflow:hidden;">
<?php require __DIR__ . '/../partials/header.php'; ?>
<?php require __DIR__ . '/../pages/' . $page['view'] . '.php'; ?>
<?php require __DIR__ . '/../partials/footer.php'; ?>

<!-- Website Closed Overlay -->
<div id="site-closed-overlay" style="
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.85);
  z-index: 999999;
  display: flex;
  align-items: center;
  justify-content: center;
  pointer-events: all;
">
  <div style="
    background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
    border: 2px solid #e94560;
    border-radius: 16px;
    padding: 40px 50px;
    text-align: center;
    max-width: 500px;
    width: 90%;
    box-shadow: 0 0 40px rgba(233, 69, 96, 0.4);
    font-family: 'Arial', sans-serif;
  ">
    <div style="font-size: 60px; margin-bottom: 15px;">&#128683;</div>
    <h1 style="
      color: #e94560;
      font-size: 24px;
      margin: 0 0 15px 0;
      text-transform: uppercase;
      letter-spacing: 2px;
    ">WEBSITE ĐÃ ĐÓNG</h1>
    <p style="
      color: #ccc;
      font-size: 16px;
      line-height: 1.6;
      margin: 0 0 25px 0;
    ">Website này đã ngừng hoạt động.<br>Vui lòng liên hệ để biết thêm thông tin.</p>
    <div style="
      background: rgba(233, 69, 96, 0.15);
      border: 1px solid #e94560;
      border-radius: 10px;
      padding: 18px 20px;
      margin-bottom: 20px;
    ">
      <p style="color: #fff; font-size: 14px; margin: 0 0 8px 0;">Liên hệ Zalo:</p>
      <p style="
        color: #e94560;
        font-size: 28px;
        font-weight: bold;
        margin: 0;
        letter-spacing: 2px;
      ">0334333196</p>
    </div>
    <a href="https://zalo.me/0334333196" target="_blank" style="
      display: inline-block;
      background: #e94560;
      color: #fff;
      text-decoration: none;
      padding: 12px 30px;
      border-radius: 8px;
      font-size: 16px;
      font-weight: bold;
      transition: background 0.3s;
    ">Nhắn tin Zalo</a>
  </div>
</div>
<script>
  // Prevent scrolling
  document.body.style.overflow = 'hidden';
  window.addEventListener('scroll', function(e) { window.scrollTo(0, 0); }, { passive: false });
  // Prevent keyboard shortcuts (F12, Ctrl+U, Ctrl+Shift+I, etc.)
  document.addEventListener('keydown', function(e) {
    if (e.key === 'F12' || (e.ctrlKey && e.shiftKey && (e.key === 'I' || e.key === 'J' || e.key === 'C')) || (e.ctrlKey && e.key === 'u')) {
      e.preventDefault();
    }
  });
  // Prevent right-click context menu
  document.addEventListener('contextmenu', function(e) { e.preventDefault(); });
</script>
