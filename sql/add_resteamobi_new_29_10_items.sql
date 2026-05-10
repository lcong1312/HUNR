-- Add ResTeamobiNew29-10 resources.
-- Source: C:\Users\Le Cong\Downloads\ResTeamobiNew29-10

INSERT INTO `nr_clan_image` (`id`, `name`, `images`, `gold`, `gem`, `is_sale`) VALUES
(120, 'Đeo lưng Teamobi 29/10 1', '[16140,16141,16142,16143]', 0, 0, 0),
(121, 'Đeo lưng Teamobi 29/10 2', '[16175,16176,16177,16178,16179,16180]', 0, 0, 0),
(122, 'Đeo lưng Teamobi 29/10 3', '[16217,16218,16219,16220,16221,16222]', 0, 0, 0)
ON DUPLICATE KEY UPDATE
    `name` = VALUES(`name`),
    `images` = VALUES(`images`),
    `gold` = VALUES(`gold`),
    `gem` = VALUES(`gem`),
    `is_sale` = VALUES(`is_sale`);

INSERT INTO `nr_part` (`id`, `type`, `part`, `note`) VALUES
(2186, 0, '[{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 pet head'),
(2187, 1, '[{"id":2955,"dx":0,"dy":0},{"id":16066,"dx":-4,"dy":-9},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 pet body'),
(2188, 2, '[{"id":16072,"dx":6,"dy":-5},{"id":16067,"dx":-3,"dy":-1},{"id":16068,"dx":-3,"dy":-16},{"id":16069,"dx":-1,"dy":-16},{"id":16070,"dx":-5,"dy":-17},{"id":16071,"dx":-1,"dy":-17},{"id":16072,"dx":-3,"dy":-15},{"id":16068,"dx":-8,"dy":-10},{"id":16073,"dx":-9,"dy":-14},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 pet leg'),
(2189, 0, '[{"id":16075,"dx":-1,"dy":-8},{"id":16076,"dx":-8,"dy":-10},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 1 head'),
(2190, 1, '[{"id":16077,"dx":3,"dy":-3},{"id":16078,"dx":1,"dy":-5},{"id":16079,"dx":2,"dy":-9},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":16080,"dx":2,"dy":-6},{"id":16081,"dx":3,"dy":-3},{"id":16082,"dx":1,"dy":-6},{"id":16083,"dx":-1,"dy":-7},{"id":16084,"dx":-3,"dy":-8},{"id":16085,"dx":0,"dy":-10},{"id":16086,"dx":2,"dy":-8},{"id":16087,"dx":-1,"dy":-7},{"id":16088,"dx":1,"dy":-6},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 1 body'),
(2191, 2, '[{"id":16089,"dx":5,"dy":-1},{"id":16090,"dx":-5,"dy":-5},{"id":16091,"dx":-6,"dy":-7},{"id":16092,"dx":-1,"dy":-13},{"id":16093,"dx":-2,"dy":-13},{"id":16094,"dx":2,"dy":-13},{"id":16095,"dx":1,"dy":-13},{"id":16096,"dx":-4,"dy":-3},{"id":16097,"dx":-4,"dy":-4},{"id":16098,"dx":-1,"dy":-7},{"id":16099,"dx":-6,"dy":-2},{"id":16100,"dx":-5,"dy":0},{"id":16101,"dx":-2,"dy":-5},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 1 leg'),
(2192, 0, '[{"id":16104,"dx":-7,"dy":-13},{"id":16105,"dx":-17,"dy":-16},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 2 head'),
(2193, 1, '[{"id":16106,"dx":-10,"dy":-3},{"id":16107,"dx":-6,"dy":-6},{"id":16108,"dx":-9,"dy":-7},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":16109,"dx":-4,"dy":-8},{"id":16110,"dx":-1,"dy":-3},{"id":16111,"dx":-6,"dy":-6},{"id":16112,"dx":-6,"dy":-7},{"id":16113,"dx":-4,"dy":-7},{"id":16114,"dx":-3,"dy":-9},{"id":16115,"dx":-1,"dy":-7},{"id":16116,"dx":-8,"dy":-5},{"id":16117,"dx":-11,"dy":-5},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 2 body'),
(2194, 2, '[{"id":16118,"dx":7,"dy":6},{"id":16119,"dx":-1,"dy":-3},{"id":16120,"dx":2,"dy":-1},{"id":16121,"dx":-6,"dy":-11},{"id":16122,"dx":-3,"dy":-13},{"id":16123,"dx":-2,"dy":-13},{"id":16124,"dx":-3,"dy":-12},{"id":16125,"dx":0,"dy":2},{"id":16126,"dx":1,"dy":0},{"id":16127,"dx":-2,"dy":-4},{"id":16128,"dx":-10,"dy":0},{"id":16129,"dx":-8,"dy":-1},{"id":16130,"dx":-1,"dy":-5},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 2 leg'),
(2195, 0, '[{"id":16145,"dx":3,"dy":1},{"id":16146,"dx":3,"dy":1},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 3 head'),
(2196, 1, '[{"id":16147,"dx":-2,"dy":1},{"id":16148,"dx":-3,"dy":-1},{"id":16149,"dx":-5,"dy":-3},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":16150,"dx":0,"dy":0},{"id":16151,"dx":-1,"dy":-1},{"id":16152,"dx":-7,"dy":-2},{"id":16153,"dx":-5,"dy":0},{"id":16154,"dx":-3,"dy":-2},{"id":16155,"dx":-1,"dy":-1},{"id":16156,"dx":-1,"dy":-3},{"id":16157,"dx":0,"dy":-2},{"id":16158,"dx":-4,"dy":-1},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 3 body'),
(2197, 2, '[{"id":16159,"dx":7,"dy":7},{"id":16160,"dx":-3,"dy":1},{"id":16161,"dx":-1,"dy":-1},{"id":16162,"dx":-1,"dy":-10},{"id":16163,"dx":-1,"dy":-9},{"id":16164,"dx":1,"dy":-9},{"id":16165,"dx":-1,"dy":-8},{"id":16166,"dx":-1,"dy":2},{"id":16167,"dx":-3,"dy":0},{"id":16168,"dx":0,"dy":-1},{"id":16169,"dx":1,"dy":-2},{"id":16170,"dx":0,"dy":1},{"id":16171,"dx":-1,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 costume 3 leg'),
(2198, 0, '[{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 follow pet head'),
(2199, 1, '[{"id":2955,"dx":0,"dy":0},{"id":16182,"dx":-30,"dy":-46},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 follow pet body'),
(2200, 2, '[{"id":16183,"dx":-29,"dy":-40},{"id":16181,"dx":-36,"dy":-14},{"id":16183,"dx":-33,"dy":-52},{"id":16184,"dx":-29,"dy":-54},{"id":16185,"dx":-35,"dy":-52},{"id":16186,"dx":-32,"dy":-53},{"id":16185,"dx":-34,"dy":-53},{"id":16185,"dx":-47,"dy":-48},{"id":16186,"dx":-46,"dy":-46},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0}]', 'ResTeamobiNew29-10 follow pet leg')
ON DUPLICATE KEY UPDATE
    `type` = VALUES(`type`),
    `part` = VALUES(`part`),
    `note` = VALUES(`note`);

INSERT INTO `nr_item`
    (`id`, `name`, `type`, `gender`, `description`, `level`, `require`, `resale_price`, `icon`, `part`, `is_up_to_up`, `head`, `body`, `leg`, `options`, `mount_id`, `lock`)
VALUES
    (2484, 'Pet Teamobi 29/10', 18, 3, 'Pet Teamobi 29/10', 0, 0, -1, 16074, -1, 0, 2186, 2187, 2188, NULL, -1, 1),
    (2485, 'Cải trang Teamobi 29/10 1', 5, 3, 'Cải trang Teamobi 29/10', 1, 0, 1, 16102, -1, 0, 2189, 2190, 2191, '[]', -1, 1),
    (2488, 'Cải trang Teamobi 29/10 2', 5, 3, 'Cải trang Teamobi 29/10', 1, 0, 1, 16131, -1, 0, 2192, 2193, 2194, '[]', -1, 1),
    (2489, 'Cải trang Teamobi 29/10 3', 5, 3, 'Cải trang Teamobi 29/10', 1, 0, 1, 16172, -1, 0, 2195, 2196, 2197, '[]', -1, 1),
    (2490, 'Đeo lưng Teamobi 29/10 1', 11, 3, 'Đeo lưng Teamobi 29/10', 0, 0, -1, 16144, 120, 0, -1, -1, -1, NULL, -1, 1),
    (2491, 'Đeo lưng Teamobi 29/10 2', 11, 3, 'Đeo lưng Teamobi 29/10', 0, 0, -1, 16174, 121, 0, -1, -1, -1, NULL, -1, 1),
    (2492, 'Pet đi theo Teamobi 29/10', 38, 3, 'Pet đi theo Teamobi 29/10', 0, 0, -1, 16187, 0, 0, -1, -1, -1, NULL, -1, 1),
    (2493, 'Đeo lưng Teamobi 29/10 3', 11, 3, 'Đeo lưng Teamobi 29/10', 0, 0, -1, 16223, 122, 0, -1, -1, -1, NULL, -1, 1)
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
