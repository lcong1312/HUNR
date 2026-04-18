-- Skill 27: Bien hinh 3 hanh tinh
-- Tuong thich schema hien tai cua Hunr2026

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    27,
    0,
    'Biến hình',
    'Biến hình thành cấp độ mới giúp tăng chỉ số',
    5,
    1,
    3,
    31142,
    'Tăng #% HP, KI và sức đánh',
    '[{"power_require":10000000,"damage":40,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":50,"cool_down":120000,"id":156,"point":1,"more_info":"Biến hình 1"},{"power_require":500000000000,"damage":50,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":40,"cool_down":120000,"id":157,"point":2,"more_info":"Biến hình 2"},{"power_require":1000000000000,"damage":60,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":30,"cool_down":120000,"id":158,"point":3,"more_info":"Biến hình 3"},{"power_require":2000000000000,"damage":70,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":20,"cool_down":120000,"id":159,"point":4,"more_info":"Biến hình 4"},{"power_require":3000000000000,"damage":80,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":10,"cool_down":120000,"id":160,"point":5,"more_info":"Biến hình 5"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 27 AND `class` = 0
);

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    27,
    1,
    'Biến hình',
    'Biến hình thành cấp độ mới giúp tăng chỉ số',
    5,
    1,
    3,
    31142,
    'Tăng #% HP, KI và sức đánh',
    '[{"power_require":10000000,"damage":40,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":50,"cool_down":120000,"id":156,"point":1,"more_info":"Biến hình 1"},{"power_require":500000000000,"damage":50,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":40,"cool_down":120000,"id":157,"point":2,"more_info":"Biến hình 2"},{"power_require":1000000000000,"damage":60,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":30,"cool_down":120000,"id":158,"point":3,"more_info":"Biến hình 3"},{"power_require":2000000000000,"damage":70,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":20,"cool_down":120000,"id":159,"point":4,"more_info":"Biến hình 4"},{"power_require":3000000000000,"damage":80,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":10,"cool_down":120000,"id":160,"point":5,"more_info":"Biến hình 5"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 27 AND `class` = 1
);

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    27,
    2,
    'Biến hình',
    'Biến hình thành cấp độ mới giúp tăng chỉ số',
    5,
    1,
    3,
    31142,
    'Tăng #% HP, KI và sức đánh',
    '[{"power_require":10000000,"damage":40,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":50,"cool_down":120000,"id":156,"point":1,"more_info":"Biến hình 1"},{"power_require":500000000000,"damage":50,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":40,"cool_down":120000,"id":157,"point":2,"more_info":"Biến hình 2"},{"power_require":1000000000000,"damage":60,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":30,"cool_down":120000,"id":158,"point":3,"more_info":"Biến hình 3"},{"power_require":2000000000000,"damage":70,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":20,"cool_down":120000,"id":159,"point":4,"more_info":"Biến hình 4"},{"power_require":3000000000000,"damage":80,"dx":0,"dy":0,"price":9999,"max_fight":1,"mana_use":10,"cool_down":120000,"id":160,"point":5,"more_info":"Biến hình 5"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 27 AND `class` = 2
);

INSERT INTO `nr_item` (
    `id`, `name`, `type`, `gender`, `description`, `level`, `require`,
    `resale_price`, `icon`, `part`, `is_up_to_up`, `head`, `body`, `leg`,
    `options`, `mount_id`, `lock`
)
VALUES
    (1326, 'Biến Hình Lv1', 7, 0, 'Học Biến Hình Trái Đất', 1, 150000000, -1, 31254, -1, 1, -1, -1, -1, '[{"param":40,"id":50},{"param":40,"id":77},{"param":40,"id":103}]', -1, 1),
    (1327, 'Biến Hình Lv2', 7, 0, 'Học Biến Hình Trái Đất LV2', 2, 150000000, -1, 31256, -1, 1, -1, -1, -1, '[{"param":50,"id":50},{"param":50,"id":77},{"param":50,"id":103}]', -1, 1),
    (1328, 'Biến Hình Lv3', 7, 0, 'Học Biến Hình Trái Đất LV3', 3, 150000000, -1, 31257, -1, 1, -1, -1, -1, '[{"param":60,"id":50},{"param":60,"id":77},{"param":60,"id":103}]', -1, 1),
    (1329, 'Biến Hình Lv4', 7, 0, 'Học Biến Hình Trái Đất LV4', 4, 150000000, -1, 31258, -1, 1, -1, -1, -1, '[{"param":70,"id":50},{"param":70,"id":77},{"param":70,"id":103}]', -1, 1),
    (1330, 'Biến Hình Lv5', 7, 0, 'Học Biến Hình Trái Đất LV5', 5, 150000000, -1, 31259, -1, 1, -1, -1, -1, '[{"param":80,"id":50},{"param":80,"id":77},{"param":80,"id":103}]', -1, 1),
    (1331, 'Biến Hình Lv1', 7, 1, 'Học Biến Hình Namec LV1', 1, 150000000, -1, 31267, -1, 1, -1, -1, -1, '[{"param":40,"id":50},{"param":40,"id":77},{"param":40,"id":103}]', -1, 1),
    (1332, 'Biến Hình Lv2', 7, 1, 'Học Biến Hình Namec LV2', 2, 150000000, -1, 31268, -1, 1, -1, -1, -1, '[{"param":50,"id":50},{"param":50,"id":77},{"param":50,"id":103}]', -1, 1),
    (1333, 'Biến Hình Lv3', 7, 1, 'Học Biến Hình Namec LV3', 3, 150000000, -1, 31269, -1, 1, -1, -1, -1, '[{"param":60,"id":50},{"param":60,"id":77},{"param":60,"id":103}]', -1, 1),
    (1334, 'Biến Hình Lv4', 7, 1, 'Học Biến Hình Namec LV4', 4, 150000000, -1, 31270, -1, 1, -1, -1, -1, '[{"param":70,"id":50},{"param":70,"id":77},{"param":70,"id":103}]', -1, 1),
    (1335, 'Biến Hình Lv5', 7, 1, 'Học Biến Hình Namec LV5', 5, 150000000, -1, 31271, -1, 1, -1, -1, -1, '[{"param":80,"id":50},{"param":80,"id":77},{"param":80,"id":103}]', -1, 1),
    (1336, 'Biến Hình Lv1', 7, 2, 'Học Biến Hình Xayda LV1', 1, 150000000, -1, 31261, -1, 1, -1, -1, -1, '[{"param":40,"id":50},{"param":40,"id":77},{"param":40,"id":103}]', -1, 1),
    (1337, 'Biến Hình Lv2', 7, 2, 'Học Biến Hình Xayda LV2', 2, 150000000, -1, 31262, -1, 1, -1, -1, -1, '[{"param":50,"id":50},{"param":50,"id":77},{"param":50,"id":103}]', -1, 1),
    (1338, 'Biến Hình Lv3', 7, 2, 'Học Biến Hình Xayda LV3', 3, 150000000, -1, 31263, -1, 1, -1, -1, -1, '[{"param":60,"id":50},{"param":60,"id":77},{"param":60,"id":103}]', -1, 1),
    (1339, 'Biến Hình Lv4', 7, 2, 'Học Biến Hình Xayda LV4', 4, 150000000, -1, 31264, -1, 1, -1, -1, -1, '[{"param":70,"id":50},{"param":70,"id":77},{"param":70,"id":103}]', -1, 1),
    (1340, 'Biến Hình Lv5', 7, 2, 'Học Biến Hình Xayda LV5', 5, 150000000, -1, 31265, -1, 1, -1, -1, -1, '[{"param":80,"id":50},{"param":80,"id":77},{"param":80,"id":103}]', -1, 1)
ON DUPLICATE KEY UPDATE
    `name` = VALUES(`name`),
    `type` = VALUES(`type`),
    `gender` = VALUES(`gender`),
    `description` = VALUES(`description`),
    `level` = VALUES(`level`),
    `require` = VALUES(`require`),
    `resale_price` = VALUES(`resale_price`),
    `icon` = VALUES(`icon`),
    `part` = VALUES(`part`),
    `is_up_to_up` = VALUES(`is_up_to_up`),
    `head` = VALUES(`head`),
    `body` = VALUES(`body`),
    `leg` = VALUES(`leg`),
    `options` = VALUES(`options`),
    `mount_id` = VALUES(`mount_id`),
    `lock` = VALUES(`lock`);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_13_0.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_13_0.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_13_1.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_13_1.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_6_0.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_6_0.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_7_0.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_7_0.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_31_0.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_31_0.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_9_1.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_9_1.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_30_0.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_30_0.png'
);

INSERT INTO `nr_image_by_name` (`filename`, `n_frame`)
SELECT 'aura_31_1.png', 4
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_image_by_name` WHERE `filename` = 'aura_31_1.png'
);

INSERT INTO `nr_part` (`id`, `type`, `part`, `note`) VALUES
    (1394, 1, '[{"id":31042,"dx":-1,"dy":-8},{"id":31043,"dx":-5,"dy":-8},{"id":31045,"dx":-5,"dy":-8},{"id":31046,"dx":-8,"dy":-7},{"id":31047,"dx":-7,"dy":-7},{"id":31048,"dx":0,"dy":-7},{"id":31049,"dx":-1,"dy":-8},{"id":31050,"dx":-2,"dy":-5},{"id":31051,"dx":-7,"dy":-15},{"id":31052,"dx":-8,"dy":-8},{"id":31053,"dx":-3,"dy":-7},{"id":31054,"dx":-5,"dy":-7},{"id":31055,"dx":-2,"dy":-7},{"id":31056,"dx":2,"dy":-13},{"id":31057,"dx":0,"dy":-7},{"id":31058,"dx":-2,"dy":-9},{"id":16,"dx":0,"dy":0}]', '  '),
    (1395, 2, '[{"id":31059,"dx":1,"dy":1},{"id":31060,"dx":-3,"dy":-8},{"id":31061,"dx":-12,"dy":-6},{"id":31062,"dx":-8,"dy":-5},{"id":31063,"dx":-4,"dy":-3},{"id":31064,"dx":-7,"dy":-4},{"id":31065,"dx":-2,"dy":-4},{"id":31067,"dx":-2,"dy":-5},{"id":31068,"dx":-2,"dy":-1},{"id":31069,"dx":-2,"dy":-7},{"id":31070,"dx":-4,"dy":-9},{"id":31071,"dx":-3,"dy":-7},{"id":31072,"dx":-1,"dy":-4},{"id":34,"dx":0,"dy":0}]', '  '),
    (1396, 1, '[{"id":31083,"dx":0,"dy":-7},{"id":31084,"dx":-5,"dy":-7},{"id":31085,"dx":-1,"dy":-5},{"id":31086,"dx":3,"dy":-5},{"id":31087,"dx":0,"dy":-5},{"id":31088,"dx":-6,"dy":-5},{"id":31089,"dx":-4,"dy":-5},{"id":31091,"dx":-1,"dy":-4},{"id":31092,"dx":-4,"dy":-13},{"id":31093,"dx":-7,"dy":-7},{"id":31094,"dx":-1,"dy":-5},{"id":31095,"dx":-2,"dy":-7},{"id":31096,"dx":0,"dy":-5},{"id":31097,"dx":5,"dy":-12},{"id":31098,"dx":3,"dy":-7},{"id":31099,"dx":-1,"dy":-7},{"id":16,"dx":0,"dy":0}]', '  '),
    (1397, 2, '[{"id":31100,"dx":2,"dy":1},{"id":31101,"dx":-3,"dy":-7},{"id":31106,"dx":-8,"dy":-5},{"id":31102,"dx":-2,"dy":-1},{"id":31104,"dx":-9,"dy":-3},{"id":31105,"dx":-5,"dy":-1},{"id":31107,"dx":-4,"dy":-2},{"id":31108,"dx":-1,"dy":-4},{"id":31109,"dx":-3,"dy":-3},{"id":31110,"dx":0,"dy":-6},{"id":31111,"dx":1,"dy":-5},{"id":31112,"dx":1,"dy":-3},{"id":31113,"dx":3,"dy":-3},{"id":34,"dx":0,"dy":0}]', '  '),
    (1398, 1, '[{"id":31001,"dx":0,"dy":-8},{"id":31002,"dx":-4,"dy":-8},{"id":31007,"dx":-4,"dy":-6},{"id":31008,"dx":-7,"dy":-7},{"id":31004,"dx":-8,"dy":-6},{"id":31005,"dx":-1,"dy":-6},{"id":31006,"dx":-3,"dy":-7},{"id":31009,"dx":0,"dy":-5},{"id":31010,"dx":-6,"dy":-17},{"id":31011,"dx":-7,"dy":-10},{"id":31012,"dx":-2,"dy":-6},{"id":31013,"dx":-3,"dy":-9},{"id":31015,"dx":-4,"dy":-8},{"id":31014,"dx":3,"dy":-14},{"id":31016,"dx":1,"dy":-9},{"id":31017,"dx":-1,"dy":-8},{"id":16,"dx":0,"dy":0}]', '  '),
    (1399, 2, '[{"id":31018,"dx":2,"dy":0},{"id":31019,"dx":-2,"dy":-7},{"id":31025,"dx":-10,"dy":-6},{"id":31021,"dx":-8,"dy":-3},{"id":31022,"dx":-4,"dy":-4},{"id":31023,"dx":-11,"dy":-2},{"id":31024,"dx":-9,"dy":-4},{"id":31026,"dx":0,"dy":-4},{"id":31027,"dx":-3,"dy":-4},{"id":31028,"dx":0,"dy":-7},{"id":31029,"dx":-4,"dy":-10},{"id":31030,"dx":-3,"dy":-7},{"id":31031,"dx":2,"dy":-5},{"id":34,"dx":0,"dy":0}]', '  '),
    (1400, 0, '[{"id":31143,"dx":-1,"dy":-14},{"id":31144,"dx":-2,"dy":-13},{"id":20,"dx":0,"dy":0}]', '  '),
    (1401, 0, '[{"id":31081,"dx":-14,"dy":-18},{"id":31082,"dx":-14,"dy":-18},{"id":20,"dx":0,"dy":0}]', '  '),
    (1402, 0, '[{"id":31077,"dx":-3,"dy":-14},{"id":31078,"dx":-4,"dy":-13},{"id":20,"dx":0,"dy":0}]', '  '),
    (1403, 0, '[{"id":31075,"dx":-1,"dy":-14},{"id":31076,"dx":-2,"dy":-13},{"id":20,"dx":0,"dy":0}]', '  '),
    (1404, 0, '[{"id":31079,"dx":-4,"dy":-14},{"id":31080,"dx":-4,"dy":-13},{"id":20,"dx":0,"dy":0}]', '  '),
    (1405, 0, '[{"id":31120,"dx":2,"dy":-8},{"id":31121,"dx":4,"dy":-6},{"id":20,"dx":0,"dy":0}]', '  '),
    (1406, 0, '[{"id":31116,"dx":2,"dy":-8},{"id":31117,"dx":4,"dy":-6},{"id":20,"dx":0,"dy":0}]', '  '),
    (1407, 0, '[{"id":31122,"dx":2,"dy":-8},{"id":31123,"dx":4,"dy":-6},{"id":20,"dx":0,"dy":0}]', '  '),
    (1408, 0, '[{"id":31118,"dx":2,"dy":-8},{"id":31119,"dx":4,"dy":-6},{"id":20,"dx":0,"dy":0}]', '  '),
    (1409, 0, '[{"id":31124,"dx":1,"dy":-8},{"id":31125,"dx":1,"dy":-8},{"id":20,"dx":0,"dy":0}]', '  '),
    (1410, 0, '[{"id":31032,"dx":0,"dy":-15},{"id":31033,"dx":-1,"dy":-12},{"id":20,"dx":0,"dy":0}]', '  '),
    (1411, 0, '[{"id":31034,"dx":0,"dy":-15},{"id":31035,"dx":-1,"dy":-12},{"id":20,"dx":0,"dy":0}]', '  '),
    (1412, 0, '[{"id":31036,"dx":0,"dy":-15},{"id":31037,"dx":-1,"dy":-12},{"id":20,"dx":0,"dy":0}]', '  '),
    (1413, 0, '[{"id":31038,"dx":0,"dy":-15},{"id":31039,"dx":-1,"dy":-12},{"id":20,"dx":0,"dy":0}]', '  '),
    (1414, 0, '[{"id":31040,"dx":0,"dy":-15},{"id":31041,"dx":-1,"dy":-12},{"id":20,"dx":0,"dy":0}]', '  ')
ON DUPLICATE KEY UPDATE
    `type` = VALUES(`type`),
    `part` = VALUES(`part`),
    `note` = VALUES(`note`);
