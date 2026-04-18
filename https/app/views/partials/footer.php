<?php
declare(strict_types=1);

$links = $site['links'];
?>
<div class="bg_tree"></div>
<div class="foot_bg"></div>
<div class="left_b_bottom">
    <div class="right_b_bottom">
        <div class="footer">
            <div class="left_bottom"></div>
            <div class="right_bottom"></div>
        </div>
    </div>
</div>

<script src="<?= e(asset('vendor/jquery-3.7.1.min.js')) ?>"></script>
<script src="<?= e(asset('js/ThreeCanvas.js')) ?>"></script>
<script src="<?= e(asset('js/Snow3d.js')) ?>"></script>
<script src="<?= e(asset('js/animation.js')) ?>"></script>
<script src="<?= e(asset('js/tpsk.js')) ?>"></script>
<?php foreach ($page['extra_js'] as $js): ?>
<script src="<?= e(asset($js)) ?>"></script>
<?php endforeach; ?>
<script>
    (function ($) {
        "use strict";

        $(function () {
            if ($("#backTop").length) {
                var scrollTrigger = 100;
                var backToTop = function () {
                    var scrollTop = $(window).scrollTop();
                    if (scrollTop > scrollTrigger) {
                        $("#backTop").addClass("show");
                    } else {
                        $("#backTop").removeClass("show");
                    }
                };

                backToTop();

                $(window).on("scroll", function () {
                    backToTop();
                });

                $("#backTop").on("click", function (event) {
                    event.preventDefault();
                    $("html,body").animate({
                        scrollTop: 0
                    }, 700);
                });
            }
        });
    })(jQuery);
</script>
</body>
</html>
