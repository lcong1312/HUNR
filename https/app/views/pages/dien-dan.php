<?php
declare(strict_types=1);

$forumDownloadLinks = [
    [
        'label' => 'PC Windows',
        'image' => asset('images/pc.png'),
        'href' => route_path('/trang-chu'),
    ],
    [
        'label' => 'Android APK',
        'image' => asset('images/android.png'),
        'href' => route_path('/trang-chu'),
    ],
];

$forumPostIcon = asset('images/forum-post-icon.png');
?>
<div class="bg-content forum-page">
    <div class="content">
<?php if ($flash !== null && ($flash['context'] ?? '') === 'forum'): ?>
        <div class="auth-alert auth-alert--<?= e($flash['type']) ?>"><?= e($flash['message']) ?></div>
<?php endif; ?>
<?php if (!empty($forumError)): ?>
        <div class="auth-alert auth-alert--error"><?= e($forumError) ?></div>
<?php endif; ?>

        <div class="forum-board">
            <section class="forum-hero-panel">
                <div class="forum-download-bar">
<?php foreach ($forumDownloadLinks as $link): ?>
                    <a class="forum-download-button" href="<?= e($link['href']) ?>" aria-label="<?= e($link['label']) ?>">
                        <img src="<?= e($link['image']) ?>" alt="<?= e($link['label']) ?>">
                    </a>
<?php endforeach; ?>
                </div>
            </section>

<?php if ($forumCurrentPost !== null): ?>
            <div class="forum-detail-toolbar">
                <a class="forum-back-link forum-back-link--plain" href="<?= e(route_path('/dien-dan')) ?>">Quay lại</a>
            </div>

            <div class="forum-detail-post">
                <table class="forum-detail-post__table" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="forum-detail-post__author-col">
                            <div class="forum-detail-post__avatar-box">
                                <img src="<?= e($forumPostIcon) ?>" alt="Admin" width="32" height="32">
                            </div>
                            <span class="forum-detail-post__author-name">Admin</span>
                        </td>
                        <td class="forum-detail-post__body-col">
                            <div class="forum-detail-post__meta">
                                <span><?= e((string) $forumCurrentPost['relative_created_at']) ?></span>
                                <span>#<?= e((string) $forumCurrentPost['id']) ?></span>
                            </div>
                            <div class="forum-detail-post__content">
                                <div class="forum-detail-post__title"><?= e((string) $forumCurrentPost['title']) ?></div>
                                <div class="forum-detail-post__text"><?= forum_render_content((string) $forumCurrentPost['content']) ?></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="forum-section-head forum-section-head--secondary">
                <span>Bài viết mới</span>
            </div>

            <div class="forum-topic-list forum-topic-list--compact">
<?php foreach ($forumPosts as $post): ?>
<?php if ((int) $post['id'] === (int) $forumCurrentPost['id']): ?>
<?php continue; ?>
<?php endif; ?>
                <article class="forum-topic">
                    <div class="forum-post-mark forum-post-mark--small">
                        <img src="<?= e($forumPostIcon) ?>" alt="Post icon">
                    </div>
                    <div class="forum-topic__body">
                        <a class="forum-topic__title" href="<?= e(route_path('/dien-dan')) ?>?post=<?= e((string) $post['id']) ?>"><?= e((string) $post['title']) ?></a>
                        <div class="forum-topic__meta">bởi Admin • <?= e((string) $post['relative_created_at']) ?></div>
                    </div>
                </article>
<?php endforeach; ?>
            </div>
<?php else: ?>
            <div class="forum-section-head">
                <span>Top Tổng Sức Mạnh</span>
            </div>

<?php if ($forumPosts !== []): ?>
            <div class="forum-topic-list">
<?php foreach ($forumPosts as $post): ?>
                <article class="forum-topic">
                    <div class="forum-post-mark">
                        <img src="<?= e($forumPostIcon) ?>" alt="Post icon">
                    </div>
                    <div class="forum-topic__body">
                        <a class="forum-topic__title" href="<?= e(route_path('/dien-dan')) ?>?post=<?= e((string) $post['id']) ?>"><?= e((string) $post['title']) ?></a>
                        <div class="forum-topic__meta">bởi Admin • <?= e((string) $post['relative_created_at']) ?></div>
                    </div>
                    <div class="forum-post-id">#<?= e((string) $post['id']) ?></div>
                </article>
<?php endforeach; ?>
            </div>
<?php else: ?>
            <div class="forum-empty-state">
                Chưa có bài viết nào trong diễn đàn. Hãy thêm dữ liệu vào bảng <code>web_forum_posts</code> để hiển thị danh sách.
            </div>
<?php endif; ?>
<?php endif; ?>
        </div>
    </div>
</div>
