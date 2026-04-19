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

-- Skill 24/25/26: Super Kamejoko, Cađíc liên hoàn chưởng, Ma phong ba

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    24,
    0,
    'Super Kamejoko',
    'Đánh xa nhờ năng lượng',
    10,
    1,
    4,
    11162,
    'Tăng sức đánh: #%',
    '[{"power_require":60000000000,"damage":550,"dx":190,"dy":25,"price":9999,"max_fight":1,"mana_use":80,"cool_down":170000,"id":186,"point":1,"more_info":"Chưởng 1"},{"power_require":60000000000,"damage":600,"dx":200,"dy":30,"price":9999,"max_fight":1,"mana_use":75,"cool_down":160000,"id":187,"point":2,"more_info":"Chưởng 2"},{"power_require":60000000000,"damage":650,"dx":210,"dy":35,"price":9999,"max_fight":1,"mana_use":70,"cool_down":150000,"id":188,"point":3,"more_info":"Chưởng 3"},{"power_require":60000000000,"damage":700,"dx":230,"dy":40,"price":9999,"max_fight":1,"mana_use":65,"cool_down":140000,"id":189,"point":4,"more_info":"Chưởng 4"},{"power_require":60000000000,"damage":750,"dx":250,"dy":45,"price":9999,"max_fight":1,"mana_use":60,"cool_down":130000,"id":190,"point":5,"more_info":"Chưởng 5"},{"power_require":60000000000,"damage":800,"dx":270,"dy":50,"price":9999,"max_fight":1,"mana_use":55,"cool_down":120000,"id":191,"point":6,"more_info":"Chưởng 6"},{"power_require":60000000000,"damage":850,"dx":290,"dy":55,"price":9999,"max_fight":1,"mana_use":50,"cool_down":110000,"id":192,"point":7,"more_info":"Chưởng 7"},{"power_require":60000000000,"damage":900,"dx":310,"dy":60,"price":9999,"max_fight":1,"mana_use":45,"cool_down":100000,"id":193,"point":8,"more_info":"Chưởng 8"},{"power_require":60000000000,"damage":950,"dx":330,"dy":65,"price":9999,"max_fight":1,"mana_use":40,"cool_down":90000,"id":194,"point":9,"more_info":"Chưởng 9"},{"power_require":60000000000,"damage":1000,"dx":350,"dy":70,"price":9999,"max_fight":1,"mana_use":35,"cool_down":80000,"id":195,"point":10,"more_info":"Chưởng 10"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 24 AND `class` = 0
);

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    26,
    1,
    'Ma phong ba',
    'Nhốt đối thủ vào bình chứa',
    10,
    1,
    4,
    11194,
    'Gây sát thương chuẩn mỗi giây bằng #% max HP bản thân',
    '[{"power_require":60000000000,"damage":1,"dx":120,"dy":120,"price":9999,"max_fight":1,"mana_use":80,"cool_down":170000,"id":176,"point":1,"more_info":"Chưởng 1"},{"power_require":60000000000,"damage":2,"dx":130,"dy":130,"price":9999,"max_fight":1,"mana_use":75,"cool_down":160000,"id":177,"point":2,"more_info":"Chưởng 2"},{"power_require":60000000000,"damage":3,"dx":140,"dy":140,"price":9999,"max_fight":1,"mana_use":70,"cool_down":150000,"id":178,"point":3,"more_info":"Chưởng 3"},{"power_require":60000000000,"damage":4,"dx":150,"dy":150,"price":9999,"max_fight":1,"mana_use":65,"cool_down":140000,"id":179,"point":4,"more_info":"Chưởng 4"},{"power_require":60000000000,"damage":5,"dx":160,"dy":160,"price":9999,"max_fight":1,"mana_use":60,"cool_down":130000,"id":180,"point":5,"more_info":"Chưởng 5"},{"power_require":60000000000,"damage":6,"dx":170,"dy":170,"price":9999,"max_fight":1,"mana_use":55,"cool_down":120000,"id":181,"point":6,"more_info":"Chưởng 6"},{"power_require":60000000000,"damage":7,"dx":180,"dy":180,"price":9999,"max_fight":1,"mana_use":50,"cool_down":110000,"id":182,"point":7,"more_info":"Chưởng 7"},{"power_require":60000000000,"damage":8,"dx":190,"dy":190,"price":9999,"max_fight":1,"mana_use":45,"cool_down":100000,"id":183,"point":8,"more_info":"Chưởng 8"},{"power_require":60000000000,"damage":9,"dx":200,"dy":200,"price":9999,"max_fight":1,"mana_use":40,"cool_down":90000,"id":184,"point":9,"more_info":"Chưởng 9"},{"power_require":60000000000,"damage":10,"dx":210,"dy":210,"price":9999,"max_fight":1,"mana_use":35,"cool_down":80000,"id":185,"point":10,"more_info":"Chưởng 10"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 26 AND `class` = 1
);

INSERT INTO `nr_skill` (
    `skill_id`, `class`, `name`, `description`, `max_point`,
    `mana_use_type`, `type`, `icon`, `info`, `skills`
)
SELECT
    25,
    2,
    'Cađíc liên hoàn chưởng',
    'Đánh xa nhờ năng lượng',
    10,
    1,
    4,
    11193,
    'Tăng sức đánh: #%',
    '[{"power_require":60000000000,"damage":550,"dx":83,"dy":83,"price":9999,"max_fight":1,"mana_use":80,"cool_down":170000,"id":166,"point":1,"more_info":"Chưởng 1"},{"power_require":60000000000,"damage":600,"dx":95,"dy":95,"price":9999,"max_fight":1,"mana_use":75,"cool_down":160000,"id":167,"point":2,"more_info":"Chưởng 2"},{"power_require":60000000000,"damage":650,"dx":107,"dy":107,"price":9999,"max_fight":1,"mana_use":70,"cool_down":150000,"id":168,"point":3,"more_info":"Chưởng 3"},{"power_require":60000000000,"damage":700,"dx":119,"dy":119,"price":9999,"max_fight":1,"mana_use":65,"cool_down":140000,"id":169,"point":4,"more_info":"Chưởng 4"},{"power_require":60000000000,"damage":750,"dx":130,"dy":130,"price":9999,"max_fight":1,"mana_use":60,"cool_down":130000,"id":170,"point":5,"more_info":"Chưởng 5"},{"power_require":60000000000,"damage":800,"dx":142,"dy":142,"price":9999,"max_fight":1,"mana_use":55,"cool_down":120000,"id":171,"point":6,"more_info":"Chưởng 6"},{"power_require":60000000000,"damage":850,"dx":154,"dy":154,"price":9999,"max_fight":1,"mana_use":50,"cool_down":110000,"id":172,"point":7,"more_info":"Chưởng 7"},{"power_require":60000000000,"damage":900,"dx":165,"dy":165,"price":9999,"max_fight":1,"mana_use":45,"cool_down":100000,"id":173,"point":8,"more_info":"Chưởng 8"},{"power_require":60000000000,"damage":950,"dx":177,"dy":177,"price":9999,"max_fight":1,"mana_use":40,"cool_down":90000,"id":174,"point":9,"more_info":"Chưởng 9"},{"power_require":60000000000,"damage":1000,"dx":188,"dy":188,"price":9999,"max_fight":1,"mana_use":35,"cool_down":80000,"id":175,"point":10,"more_info":"Chưởng 10"}]'
WHERE NOT EXISTS (
    SELECT 1 FROM `nr_skill` WHERE `skill_id` = 25 AND `class` = 2
);

INSERT INTO `nr_item` (
    `id`, `name`, `type`, `gender`, `description`, `level`, `require`,
    `resale_price`, `icon`, `part`, `is_up_to_up`, `head`, `body`, `leg`,
    `options`, `mount_id`, `lock`
)
VALUES
    (1444, 'Sách Super Kamejoko lv1', 7, 0, 'Học tuyệt kỹ Super Kamejoko', 1, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1445, 'Sách Super Kamejoko lv2', 7, 0, 'Nâng Super Kamejoko lên cấp 2', 2, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1446, 'Sách Super Kamejoko lv3', 7, 0, 'Nâng Super Kamejoko lên cấp 3', 3, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1447, 'Sách Super Kamejoko lv4', 7, 0, 'Nâng Super Kamejoko lên cấp 4', 4, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1448, 'Sách Super Kamejoko lv5', 7, 0, 'Nâng Super Kamejoko lên cấp 5', 5, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1449, 'Sách Super Kamejoko lv6', 7, 0, 'Nâng Super Kamejoko lên cấp 6', 6, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1450, 'Sách Super Kamejoko lv7', 7, 0, 'Nâng Super Kamejoko lên cấp 7', 7, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1451, 'Sách Super Kamejoko lv8', 7, 0, 'Nâng Super Kamejoko lên cấp 8', 8, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1452, 'Sách Super Kamejoko lv9', 7, 0, 'Nâng Super Kamejoko lên cấp 9', 9, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1453, 'Sách Super Kamejoko lv10', 7, 0, 'Nâng Super Kamejoko lên cấp 10', 10, 150000000, -1, 11195, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1454, 'Sách Ma phong ba lv1', 7, 1, 'Học tuyệt kỹ Ma phong ba', 1, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1455, 'Sách Ma phong ba lv2', 7, 1, 'Nâng Ma phong ba lên cấp 2', 2, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1456, 'Sách Ma phong ba lv3', 7, 1, 'Nâng Ma phong ba lên cấp 3', 3, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1457, 'Sách Ma phong ba lv4', 7, 1, 'Nâng Ma phong ba lên cấp 4', 4, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1458, 'Sách Ma phong ba lv5', 7, 1, 'Nâng Ma phong ba lên cấp 5', 5, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1459, 'Sách Ma phong ba lv6', 7, 1, 'Nâng Ma phong ba lên cấp 6', 6, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1460, 'Sách Ma phong ba lv7', 7, 1, 'Nâng Ma phong ba lên cấp 7', 7, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1461, 'Sách Ma phong ba lv8', 7, 1, 'Nâng Ma phong ba lên cấp 8', 8, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1462, 'Sách Ma phong ba lv9', 7, 1, 'Nâng Ma phong ba lên cấp 9', 9, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1463, 'Sách Ma phong ba lv10', 7, 1, 'Nâng Ma phong ba lên cấp 10', 10, 150000000, -1, 11194, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1464, 'Sách Cađíc liên hoàn chưởng lv1', 7, 2, 'Học tuyệt kỹ Cađíc liên hoàn chưởng', 1, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1465, 'Sách Cađíc liên hoàn chưởng lv2', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 2', 2, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1466, 'Sách Cađíc liên hoàn chưởng lv3', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 3', 3, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1467, 'Sách Cađíc liên hoàn chưởng lv4', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 4', 4, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1468, 'Sách Cađíc liên hoàn chưởng lv5', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 5', 5, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1469, 'Sách Cađíc liên hoàn chưởng lv6', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 6', 6, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1470, 'Sách Cađíc liên hoàn chưởng lv7', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 7', 7, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1471, 'Sách Cađíc liên hoàn chưởng lv8', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 8', 8, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1472, 'Sách Cađíc liên hoàn chưởng lv9', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 9', 9, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1),
    (1473, 'Sách Cađíc liên hoàn chưởng lv10', 7, 2, 'Nâng Cađíc liên hoàn chưởng lên cấp 10', 10, 150000000, -1, 11193, -1, 1, -1, -1, -1, '[]', -1, 1)
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
