START TRANSACTION;

-- ====== CONFIG ======
SET @seed_caitrang_id_json = '[]';
SET @seed_ratio_caitrang = 30;
SET @seed_ratio_trangbi = 70;
SET @seed_used_botnames = '[]';

-- fix giới hạn concat (quan trọng)
SET SESSION group_concat_max_len = 1000000;

-- ====== GENERATE 1000 GENZ NAMES ======
SET @seed_botname_json = (
  SELECT CONCAT('[', GROUP_CONCAT(name), ']')
  FROM (
    SELECT DISTINCT CONCAT(
      '"',
      UPPER(LEFT(p.p,1)), SUBSTRING(p.p,2),
      s.s,
      LPAD(FLOOR(RAND()*100),2,'0'),
      '"'
    ) AS name
    FROM (
      SELECT 'zy' p UNION ALL SELECT 'ka' UNION ALL SELECT 'ra' UNION ALL SELECT 'lo'
      UNION ALL SELECT 'mi' UNION ALL SELECT 'xe' UNION ALL SELECT 'no' UNION ALL SELECT 'vu'
      UNION ALL SELECT 'ti' UNION ALL SELECT 'sa' UNION ALL SELECT 'ha' UNION ALL SELECT 'ki'
      UNION ALL SELECT 're' UNION ALL SELECT 'zo' UNION ALL SELECT 'fa' UNION ALL SELECT 'ne'
    ) p
    CROSS JOIN (
      SELECT 'zen' s UNION ALL SELECT 'kai' UNION ALL SELECT 'ron' UNION ALL SELECT 'to'
      UNION ALL SELECT 'shi' UNION ALL SELECT 'ra' UNION ALL SELECT 'lo' UNION ALL SELECT 'mi'
      UNION ALL SELECT 'zu' UNION ALL SELECT 'xo' UNION ALL SELECT 'ix' UNION ALL SELECT 'or'
      UNION ALL SELECT 'ex' UNION ALL SELECT 'vy' UNION ALL SELECT 'on' UNION ALL SELECT 'er'
    ) s
    CROSS JOIN (
      SELECT 0 n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
      UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
    ) n
    ORDER BY RAND()
    LIMIT 1000
  ) t
);

-- ====== UPDATE placeholder ======
UPDATE `bot_config`
SET `botname_json` = @seed_botname_json,
    `caitrang_id_json` = IFNULL(NULLIF(TRIM(`caitrang_id_json`), ''), @seed_caitrang_id_json),
    `ratio_caitrang` = IFNULL(`ratio_caitrang`, @seed_ratio_caitrang),
    `ratio_trangbi` = IFNULL(`ratio_trangbi`, @seed_ratio_trangbi),
    `is_used` = IFNULL(`is_used`, 0),
    `used_botnames` = IFNULL(NULLIF(TRIM(`used_botnames`), ''), @seed_used_botnames)
WHERE `id` = (
    SELECT `id` FROM (
        SELECT `id`
        FROM `bot_config`
        WHERE `botname_json` IS NULL
           OR TRIM(`botname_json`) IN ('', '[]', 'null')
        LIMIT 1
    ) x
)
AND NOT EXISTS (
    SELECT 1 FROM `bot_config`
    WHERE `botname_json` IS NOT NULL
      AND TRIM(`botname_json`) NOT IN ('', '[]', 'null')
);

-- ====== INSERT nếu bảng rỗng ======
INSERT INTO `bot_config` (
    `botname_json`,
    `caitrang_id_json`,
    `ratio_caitrang`,
    `ratio_trangbi`,
    `is_used`,
    `used_botnames`
)
SELECT
    @seed_botname_json,
    @seed_caitrang_id_json,
    @seed_ratio_caitrang,
    @seed_ratio_trangbi,
    0,
    @seed_used_botnames
WHERE NOT EXISTS (
    SELECT 1 FROM `bot_config`
);

COMMIT;

-- ====== CHECK ======
SELECT
    id,
    LENGTH(botname_json) AS json_size
FROM bot_config;