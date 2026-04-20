-- Port 8 cải trang từ shop đặc biệt Santa ở code cũ
-- Nguồn đối chiếu:
--   D:\SRC NRO NEW 01\Server\ngocrong.sql
--   block "Cai trang Gogeta ssj5..." + "Them 5 cai trang..." + "Them 2 cai trang..."
--
-- Tích hợp vào code mới:
--   Santa -> Shop Đặc biệt -> CMDMenu.SANTA_SPECIAL_SHOP -> Shop.getShop(NpcName.BUNMA_TET)
--   => thêm item vào nr_shop_bunma_tet tab 2 (Hồng ngọc)
--
-- Dải ID mới để tránh đè dữ liệu hiện tại:
--   nr_item: 2472-2479
--   nr_part: 2150-2173
--   nr_shop_bunma_tet: 51-58

INSERT INTO `nr_part` (`id`, `type`, `part`, `note`) VALUES
(2150, 0, '[{"id":29429,"dx":-20,"dy":-25},{"id":29430,"dx":-19,"dy":-19},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Gogeta SSJ5 head'),
(2151, 1, '[{"id":29433,"dx":-11,"dy":-6},{"id":29434,"dx":-8,"dy":-12},{"id":29435,"dx":-3,"dy":-14},{"id":29436,"dx":2,"dy":-13},{"id":29437,"dx":-2,"dy":-12},{"id":29438,"dx":0,"dy":-12},{"id":29439,"dx":0,"dy":-11},{"id":29440,"dx":-5,"dy":-13},{"id":29441,"dx":-1,"dy":-10},{"id":29442,"dx":-10,"dy":-16},{"id":29443,"dx":-7,"dy":-9},{"id":29444,"dx":-7,"dy":-11},{"id":29445,"dx":-4,"dy":-12},{"id":29446,"dx":-1,"dy":-11},{"id":29447,"dx":-7,"dy":-15},{"id":29448,"dx":-13,"dy":-10},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Gogeta SSJ5 body'),
(2152, 2, '[{"id":29449,"dx":4,"dy":0},{"id":29450,"dx":-2,"dy":-7},{"id":29451,"dx":1,"dy":-7},{"id":29452,"dx":-1,"dy":-7},{"id":29453,"dx":-7,"dy":-7},{"id":29454,"dx":-5,"dy":-8},{"id":29455,"dx":-6,"dy":-7},{"id":29456,"dx":0,"dy":-4},{"id":29457,"dx":0,"dy":-5},{"id":29458,"dx":-7,"dy":-8},{"id":29459,"dx":-9,"dy":-7},{"id":29460,"dx":-10,"dy":-5},{"id":29461,"dx":-2,"dy":-7},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Gogeta SSJ5 leg'),
(2153, 0, '[{"id":26353,"dx":-5,"dy":-24},{"id":26354,"dx":-5,"dy":-22},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Demigra head'),
(2154, 1, '[{"id":26320,"dx":-15,"dy":-28},{"id":26321,"dx":-29,"dy":-36},{"id":26322,"dx":-26,"dy":-30},{"id":26323,"dx":-17,"dy":-33},{"id":26324,"dx":-20,"dy":-28},{"id":26325,"dx":-11,"dy":-27},{"id":26326,"dx":-10,"dy":-29},{"id":26327,"dx":-15,"dy":-30},{"id":26328,"dx":-15,"dy":-41},{"id":26329,"dx":-12,"dy":-30},{"id":26330,"dx":-18,"dy":-30},{"id":26331,"dx":-7,"dy":-35},{"id":26332,"dx":-22,"dy":-33},{"id":26333,"dx":-20,"dy":-30},{"id":26334,"dx":-26,"dy":-34},{"id":26335,"dx":-25,"dy":-30},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Demigra body'),
(2155, 2, '[{"id":26336,"dx":1,"dy":-4},{"id":26337,"dx":-5,"dy":-8},{"id":26338,"dx":0,"dy":-7},{"id":26339,"dx":-2,"dy":-9},{"id":26340,"dx":-4,"dy":-6},{"id":26341,"dx":0,"dy":-5},{"id":26342,"dx":-1,"dy":-7},{"id":26343,"dx":-16,"dy":-37},{"id":26344,"dx":-5,"dy":-6},{"id":26345,"dx":-7,"dy":-8},{"id":26346,"dx":-7,"dy":-10},{"id":26347,"dx":-5,"dy":-12},{"id":26348,"dx":-4,"dy":-10},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Demigra leg'),
(2156, 0, '[{"id":16037,"dx":4,"dy":-5},{"id":16038,"dx":4,"dy":-6},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Black Fide head'),
(2157, 1, '[{"id":16039,"dx":0,"dy":-5},{"id":16040,"dx":-6,"dy":-10},{"id":16041,"dx":-1,"dy":-11},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":2955,"dx":0,"dy":0},{"id":16042,"dx":-2,"dy":-7},{"id":16043,"dx":-1,"dy":-6},{"id":16044,"dx":-1,"dy":-6},{"id":16045,"dx":2,"dy":-9},{"id":16046,"dx":-4,"dy":-8},{"id":16047,"dx":1,"dy":-8},{"id":16048,"dx":2,"dy":-8},{"id":16049,"dx":-1,"dy":-8},{"id":16050,"dx":-2,"dy":-8},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Black Fide body'),
(2158, 2, '[{"id":16051,"dx":-2,"dy":-1},{"id":16052,"dx":-5,"dy":-5},{"id":16053,"dx":-9,"dy":-2},{"id":16054,"dx":-7,"dy":-16},{"id":16055,"dx":-17,"dy":-16},{"id":16056,"dx":-9,"dy":-14},{"id":16057,"dx":-10,"dy":-15},{"id":16058,"dx":-4,"dy":-1},{"id":16059,"dx":-3,"dy":-3},{"id":16060,"dx":-3,"dy":-6},{"id":16061,"dx":-1,"dy":-8},{"id":16062,"dx":-4,"dy":-4},{"id":16063,"dx":-4,"dy":-5},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Black Fide leg'),
(2159, 0, '[{"id":25772,"dx":-7,"dy":-12},{"id":25773,"dx":-3,"dy":-14},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Cooler Titan head'),
(2160, 1, '[{"id":25774,"dx":-1,"dy":-4},{"id":25775,"dx":-5,"dy":-11},{"id":25776,"dx":-4,"dy":-16},{"id":25777,"dx":-2,"dy":-15},{"id":25778,"dx":-4,"dy":-13},{"id":25779,"dx":1,"dy":-13},{"id":25780,"dx":1,"dy":-15},{"id":25781,"dx":-3,"dy":-14},{"id":25782,"dx":0,"dy":-8},{"id":25783,"dx":-5,"dy":-8},{"id":25784,"dx":-2,"dy":-12},{"id":25785,"dx":-3,"dy":-9},{"id":25786,"dx":-2,"dy":-10},{"id":25787,"dx":-2,"dy":-12},{"id":25788,"dx":-2,"dy":-14},{"id":25789,"dx":0,"dy":-13},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Cooler Titan body'),
(2161, 2, '[{"id":25790,"dx":-8,"dy":-3},{"id":25791,"dx":-3,"dy":-5},{"id":25792,"dx":-16,"dy":-5},{"id":25793,"dx":-11,"dy":-6},{"id":25794,"dx":-16,"dy":-5},{"id":25795,"dx":-13,"dy":-5},{"id":25796,"dx":-12,"dy":-5},{"id":25797,"dx":-4,"dy":-1},{"id":25798,"dx":-4,"dy":-2},{"id":25799,"dx":-1,"dy":-6},{"id":25800,"dx":3,"dy":-9},{"id":25801,"dx":-1,"dy":-2},{"id":25802,"dx":-8,"dy":-7},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Cooler Titan leg'),
(2162, 0, '[{"id":29602,"dx":3,"dy":-1},{"id":29613,"dx":2,"dy":-3},{"id":20,"dx":0,"dy":0}]', 'Santa legacy - Zeno Sama head'),
(2163, 1, '[{"id":29624,"dx":1,"dy":-1},{"id":29628,"dx":-1,"dy":-7},{"id":29629,"dx":-2,"dy":-9},{"id":29630,"dx":1,"dy":-9},{"id":29631,"dx":-3,"dy":-9},{"id":29632,"dx":-1,"dy":-9},{"id":29633,"dx":-2,"dy":-9},{"id":29603,"dx":3,"dy":-1},{"id":29604,"dx":0,"dy":-2},{"id":29605,"dx":-6,"dy":1},{"id":29606,"dx":0,"dy":-6},{"id":29607,"dx":-1,"dy":-5},{"id":29608,"dx":3,"dy":0},{"id":29609,"dx":0,"dy":-7},{"id":29610,"dx":-2,"dy":-6},{"id":29611,"dx":-2,"dy":-4},{"id":16,"dx":0,"dy":0}]', 'Santa legacy - Zeno Sama body'),
(2164, 2, '[{"id":29612,"dx":13,"dy":3},{"id":29614,"dx":0,"dy":-6},{"id":29615,"dx":-1,"dy":-7},{"id":29616,"dx":1,"dy":-7},{"id":29617,"dx":-3,"dy":-8},{"id":29618,"dx":1,"dy":-8},{"id":29619,"dx":-1,"dy":-8},{"id":29620,"dx":4,"dy":1},{"id":29621,"dx":1,"dy":-2},{"id":29622,"dx":0,"dy":-6},{"id":29623,"dx":-4,"dy":4},{"id":29625,"dx":-6,"dy":6},{"id":29626,"dx":-2,"dy":6},{"id":34,"dx":0,"dy":0}]', 'Santa legacy - Zeno Sama leg'),
(2165, 0, '[{"id":28976,"dx":-2,"dy":-19},{"id":28977,"dx":-4,"dy":-20},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Vegeta Daima SSJ4 head'),
(2166, 1, '[{"id":28979,"dx":3,"dy":-9},{"id":28980,"dx":-8,"dy":-13},{"id":28981,"dx":-13,"dy":-14},{"id":28982,"dx":-8,"dy":-13},{"id":28983,"dx":-12,"dy":-12},{"id":28984,"dx":-11,"dy":-12},{"id":28985,"dx":-14,"dy":-11},{"id":28986,"dx":-1,"dy":-14},{"id":28987,"dx":-3,"dy":-16},{"id":28988,"dx":-4,"dy":-15},{"id":28989,"dx":-11,"dy":-10},{"id":28990,"dx":-7,"dy":-11},{"id":28991,"dx":0,"dy":-13},{"id":28992,"dx":-4,"dy":-12},{"id":28993,"dx":-7,"dy":-15},{"id":28994,"dx":-8,"dy":-8},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Vegeta Daima SSJ4 body'),
(2167, 2, '[{"id":28995,"dx":8,"dy":0},{"id":28996,"dx":-2,"dy":-8},{"id":28997,"dx":0,"dy":-7},{"id":28998,"dx":1,"dy":-5},{"id":28999,"dx":1,"dy":-5},{"id":29000,"dx":1,"dy":-7},{"id":29001,"dx":1,"dy":-7},{"id":29002,"dx":2,"dy":-4},{"id":29003,"dx":2,"dy":-5},{"id":29004,"dx":-3,"dy":-8},{"id":29005,"dx":-9,"dy":-5},{"id":29006,"dx":-10,"dy":0},{"id":29007,"dx":-4,"dy":-7},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Vegeta Daima SSJ4 leg'),
(2168, 0, '[{"id":32015,"dx":-20,"dy":-29},{"id":32018,"dx":-15,"dy":-24},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Hop The VIP head'),
(2169, 1, '[{"id":32021,"dx":-4,"dy":-11},{"id":32022,"dx":-12,"dy":-20},{"id":32023,"dx":-6,"dy":-12},{"id":32024,"dx":-7,"dy":-15},{"id":32025,"dx":-5,"dy":-12},{"id":32026,"dx":-5,"dy":-12},{"id":32027,"dx":-4,"dy":-16},{"id":32028,"dx":-5,"dy":-19},{"id":32029,"dx":-3,"dy":-19},{"id":32030,"dx":-15,"dy":-19},{"id":32031,"dx":-15,"dy":-16},{"id":32032,"dx":-16,"dy":-16},{"id":32033,"dx":0,"dy":-15},{"id":32034,"dx":-4,"dy":-15},{"id":32035,"dx":-6,"dy":-14},{"id":32036,"dx":-9,"dy":-20},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Hop The VIP body'),
(2170, 2, '[{"id":32037,"dx":3,"dy":-2},{"id":32038,"dx":-4,"dy":-12},{"id":32039,"dx":-10,"dy":-7},{"id":32040,"dx":0,"dy":-5},{"id":32041,"dx":-3,"dy":-6},{"id":32042,"dx":-1,"dy":-4},{"id":32043,"dx":3,"dy":-5},{"id":32044,"dx":-4,"dy":-7},{"id":32045,"dx":-2,"dy":-7},{"id":32046,"dx":-6,"dy":-10},{"id":32047,"dx":3,"dy":1},{"id":32048,"dx":-10,"dy":-3},{"id":32049,"dx":-4,"dy":-7},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Hop The VIP leg'),
(2171, 0, '[{"id":15868,"dx":-3,"dy":-29},{"id":15871,"dx":-2,"dy":-25},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Uhachi head'),
(2172, 1, '[{"id":15874,"dx":-12,"dy":-21},{"id":15875,"dx":-20,"dy":-24},{"id":15876,"dx":-21,"dy":-24},{"id":15877,"dx":-15,"dy":-30},{"id":15878,"dx":-21,"dy":-26},{"id":15879,"dx":-22,"dy":-23},{"id":15880,"dx":-19,"dy":-27},{"id":15881,"dx":-7,"dy":-21},{"id":15882,"dx":-12,"dy":-19},{"id":15883,"dx":-11,"dy":-20},{"id":15884,"dx":-18,"dy":-62},{"id":15885,"dx":-40,"dy":-29},{"id":15886,"dx":-9,"dy":-18},{"id":15887,"dx":-17,"dy":-21},{"id":15888,"dx":-9,"dy":-26},{"id":15889,"dx":-12,"dy":-25},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Uhachi body'),
(2173, 2, '[{"id":15890,"dx":2,"dy":1},{"id":15891,"dx":-6,"dy":-9},{"id":15892,"dx":-2,"dy":-6},{"id":15893,"dx":-2,"dy":-7},{"id":15894,"dx":-2,"dy":-5},{"id":15895,"dx":-3,"dy":-6},{"id":15896,"dx":3,"dy":-8},{"id":15897,"dx":-5,"dy":-6},{"id":15898,"dx":-1,"dy":-9},{"id":15899,"dx":-4,"dy":-7},{"id":15900,"dx":-1,"dy":-4},{"id":15901,"dx":-6,"dy":0},{"id":15902,"dx":-4,"dy":-6},{"id":2955,"dx":0,"dy":0}]', 'Santa legacy - Uhachi leg')
ON DUPLICATE KEY UPDATE
`type` = VALUES(`type`),
`part` = VALUES(`part`),
`note` = VALUES(`note`);

INSERT INTO `nr_item` (
    `id`, `name`, `type`, `gender`, `description`, `level`, `require`, `resale_price`,
    `icon`, `part`, `is_up_to_up`, `head`, `body`, `leg`, `options`, `mount_id`, `lock`
) VALUES
(2472, 'Cải trang Gogeta Super ssj5', 5, 3, 'Cải trang thành Gogeta Super ssj5', 1, 0, 1, 29463, -1, 0, 2150, 2151, 2152, '[]', -1, 1),
(2473, 'Cải trang Demigra', 5, 3, 'Cải trang thành Demigra', 1, 0, 1, 26349, -1, 0, 2153, 2154, 2155, '[]', -1, 1),
(2474, 'Cải trang Black Fide', 5, 3, 'Cải trang thành Black Fide', 1, 0, 1, 16065, -1, 0, 2156, 2157, 2158, '[]', -1, 1),
(2475, 'Cải trang Cooler Titan', 5, 3, 'Cải trang thành Cooler Titan', 1, 0, 1, 25803, -1, 0, 2159, 2160, 2161, '[]', -1, 1),
(2476, 'Cải trang Zeno Sama', 5, 3, 'Cải trang thành Zeno Sama', 1, 0, 1, 29627, -1, 0, 2162, 2163, 2164, '[]', -1, 1),
(2477, 'Cải trang Vegeta Daima Super ssj4', 5, 3, 'Cải trang thành Vegeta Daima Super ssj4', 1, 0, 1, 29008, -1, 0, 2165, 2166, 2167, '[]', -1, 1),
(2478, 'Cải trang Hợp Thể VIP', 5, 3, 'Cải trang thành Hợp Thể VIP', 1, 0, 1, 32015, -1, 0, 2168, 2169, 2170, '[]', -1, 1),
(2479, 'Cải trang Uhachi', 5, 3, 'Cải trang thành Uhachi', 1, 0, 1, 15868, -1, 0, 2171, 2172, 2173, '[]', -1, 1)
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

INSERT INTO `nr_shop_bunma_tet` (
    `id`, `item_id`, `icon_special`, `buy_special`, `options`, `expired`, `new`, `preview`, `tab`
) VALUES
(51, 2472, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(52, 2473, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(53, 2474, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(54, 2475, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(55, 2476, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(56, 2477, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(57, 2478, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2),
(58, 2479, 861, 200000, '[{"id":77,"param":40},{"id":103,"param":40},{"id":50,"param":40},{"id":101,"param":100},{"id":14,"param":20},{"id":5,"param":100},{"id":106,"param":1}]', -1, 1, 0, 2)
ON DUPLICATE KEY UPDATE
`item_id` = VALUES(`item_id`),
`icon_special` = VALUES(`icon_special`),
`buy_special` = VALUES(`buy_special`),
`options` = VALUES(`options`),
`expired` = VALUES(`expired`),
`new` = VALUES(`new`),
`preview` = VALUES(`preview`),
`tab` = VALUES(`tab`);

-- Avatar head mapping của code mới nằm trong nr_others.key = 'avatar'
UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2150,"avatar":29463}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2150%' AND `value` NOT LIKE '%"head": 2150%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2153,"avatar":26349}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2153%' AND `value` NOT LIKE '%"head": 2153%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2156,"avatar":16065}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2156%' AND `value` NOT LIKE '%"head": 2156%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2159,"avatar":25803}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2159%' AND `value` NOT LIKE '%"head": 2159%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2162,"avatar":29627}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2162%' AND `value` NOT LIKE '%"head": 2162%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2165,"avatar":29008}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2165%' AND `value` NOT LIKE '%"head": 2165%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2168,"avatar":32015}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2168%' AND `value` NOT LIKE '%"head": 2168%';

UPDATE `nr_others`
SET `value` = CONCAT(TRIM(TRAILING ']' FROM `value`), ',{"head":2171,"avatar":15868}]')
WHERE `id` = 6 AND `key` = 'avatar'
  AND `value` NOT LIKE '%"head":2171%' AND `value` NOT LIKE '%"head": 2171%';
