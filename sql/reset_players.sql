-- Reset toàn bộ dữ liệu người chơi (không xóa cấu trúc bảng)
-- Chạy trên DB hiện tại: hunr

SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;

DROP TEMPORARY TABLE IF EXISTS _reset_tables;
CREATE TEMPORARY TABLE _reset_tables (
    table_name VARCHAR(128) PRIMARY KEY
);

-- 1) Tự động gom các bảng có cột liên quan user/player
INSERT IGNORE INTO _reset_tables (table_name)
SELECT DISTINCT c.TABLE_NAME
FROM INFORMATION_SCHEMA.COLUMNS c
WHERE c.TABLE_SCHEMA = DATABASE()
  AND c.COLUMN_NAME IN (
      'player_id', 'user_id', 'account_id', 'username',
      'char_id', 'clan_id', 'leader_id', 'member_id'
  );

-- 2) Bổ sung các bảng core thường gặp
INSERT IGNORE INTO _reset_tables (table_name) VALUES
('nr_user'),
('nr_player'),
('nr_disciple'),
('nr_clan'),
('nr_clan_member'),
('history_receive_goldbar');

-- 3) Bổ sung các bảng log/lịch sử theo tên, kể cả bảng không có player_id
INSERT IGNORE INTO _reset_tables (table_name)
SELECT tb.TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES tb
WHERE tb.TABLE_SCHEMA = DATABASE()
  AND tb.TABLE_TYPE = 'BASE TABLE'
  AND (
      LOWER(tb.TABLE_NAME) LIKE '%history%'
      OR LOWER(tb.TABLE_NAME) LIKE '%log%'
  );

-- 4) Bổ sung các bảng log/giao dịch/nạp/top theo người chơi không có cột định danh chuẩn
INSERT IGNORE INTO _reset_tables (table_name) VALUES
('bank_topup_orders'),
('nr_goldbar_paid_daily'),
('nr_history_trade'),
('nr_rewardtop'),
('nr_top_boss'),
('nr_top_nap'),
('nr_top_nrsd'),
('nr_top_power');

-- 5) Loại trừ các bảng cấu hình/static để không ảnh hưởng dữ liệu game gốc
DELETE FROM _reset_tables
WHERE table_name IN (
    'nr_achievement',
    'nr_arrow',
    'nr_background_item',
    'nr_bo_mong_boss_config',
    'nr_bo_mong_config',
    'nr_bo_mong_moc_diem',
    'nr_bo_mong_nhiem_vu_config',
    'nr_caption',
    'nr_clan_image',
    'nr_collection_book'
);

DELETE FROM nr_infoclient;
DELETE FROM nr_shop_amulet;

-- 6) Chỉ giữ bảng tồn tại thật trong DB hiện tại
DELETE t
FROM _reset_tables t
LEFT JOIN INFORMATION_SCHEMA.TABLES tb
       ON tb.TABLE_SCHEMA = DATABASE()
      AND tb.TABLE_NAME = t.table_name
      AND tb.TABLE_TYPE = 'BASE TABLE'
WHERE tb.TABLE_NAME IS NULL;

-- 7) TRUNCATE từng bảng (MariaDB không PREPARE được chuỗi nhiều lệnh)
DROP PROCEDURE IF EXISTS _truncate_reset_tables;
DELIMITER $$
CREATE PROCEDURE _truncate_reset_tables()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_table VARCHAR(128);
    DECLARE cur CURSOR FOR
        SELECT table_name FROM _reset_tables ORDER BY table_name;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_table;
        IF done = 1 THEN
            LEAVE read_loop;
        END IF;

        SET @truncate_sql = CONCAT('TRUNCATE TABLE `', REPLACE(v_table, '`', '``'), '`');
        PREPARE stmt FROM @truncate_sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    CLOSE cur;
END$$
DELIMITER ;

CALL _truncate_reset_tables();
DROP PROCEDURE IF EXISTS _truncate_reset_tables;

DROP TEMPORARY TABLE IF EXISTS _reset_tables;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;

SELECT 'Reset player data completed.' AS message;
