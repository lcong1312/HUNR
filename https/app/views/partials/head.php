<?php
declare(strict_types=1);

$meta = $site['meta'];
$pageTitle = $page['title'] ?? $meta['title'];
$canonical = $page['canonical'] ?? $requestPath;
?>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <base href="<?= e(site_url()) ?>">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><?= e($pageTitle) ?></title>
  <meta name="keywords" content="<?= e($meta['keywords']) ?>">
  <meta name="description" content="<?= e($meta['description']) ?>">
  <meta http-equiv="refresh" content="<?= e($meta['refresh']) ?>">
  <meta name="robots" content="<?= e($meta['robots']) ?>">
  <meta property="og:title" content="<?= e($meta['og_title']) ?>">
  <meta property="og:description" content="<?= e($meta['og_description']) ?>">
  <meta property="og:image" content="<?= e(site_url(asset('images/4rum_280x90.png'))) ?>">
  <meta property="og:url" content="<?= e(site_url($canonical)) ?>">
  <link rel="apple-touch-icon" href="<?= e(asset('images/favicon-48x48.ico')) ?>">
  <link rel="icon" href="<?= e(asset('images/favicon-48x48.ico')) ?>" type="image/x-icon">
  <link rel="shortcut icon" href="<?= e(asset('images/favicon-48x48.ico')) ?>" type="image/x-icon">
  <link rel="icon" href="<?= e(asset('images/favicon-48x48.ico')) ?>">
  <link rel="icon" type="image/png" href="<?= e(asset('images/favicon-32x32.png')) ?>" sizes="32x32">
  <link rel="icon" type="image/png" href="<?= e(asset('images/favicon-64x64.png')) ?>" sizes="64x64">
  <link rel="icon" type="image/png" href="<?= e(asset('images/favicon-128x128.png')) ?>" sizes="128x128">
  <link rel="icon" type="image/png" href="<?= e(asset('images/favicon-48x48.png')) ?>" sizes="48x48">
  <link rel="stylesheet" href="<?= e(asset('css/template.css')) ?>">
  <link rel="stylesheet" href="<?= e(asset('css/eff.css')) ?>">
  <link rel="stylesheet" href="<?= e(asset('css/tpstyle.css')) ?>">
  <link rel="stylesheet" href="<?= e(asset('css/tpsk.css')) ?>">
  <link rel="stylesheet" href="<?= e(asset('css/auth.css')) ?>">
  <link rel="stylesheet" href="<?= e(asset('css/forum.css')) ?>">
<?php foreach ($page['extra_css'] as $css): ?>
  <link rel="stylesheet" href="<?= e(asset($css)) ?>">
<?php endforeach; ?>
</head>
