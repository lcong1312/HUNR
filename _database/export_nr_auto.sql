-- =====================================================
-- Event Scheduler: Xuất nr_player & nr_user mỗi 5 phút
-- Lưu vào bảng staging, script bên ngoài sẽ xử lý file backup
-- Yêu cầu: MariaDB 10.4+, event_scheduler=ON
-- =====================================================

SET GLOBAL event_scheduler = ON;

-- Xóa các object cũ
DROP EVENT IF EXISTS export_nr_player_every_5min;
DROP EVENT IF EXISTS export_nr_user_every_5min;
DROP EVENT IF EXISTS export_nr_disciple_every_5min;
DROP PROCEDURE IF EXISTS export_nr_player_to_sql;
DROP PROCEDURE IF EXISTS export_nr_user_to_sql;
DROP PROCEDURE IF EXISTS export_nr_disciple_to_sql;
DROP FUNCTION IF EXISTS safe_quote;

-- =====================================================
-- Bảng staging lưu nội dung .sql tạm thời
-- =====================================================
CREATE TABLE IF NOT EXISTS `_export_stage` (
    `table_name` VARCHAR(50) NOT NULL,
    `sql_line` LONGTEXT NOT NULL,
    `line_order` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`line_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELIMITER //

CREATE FUNCTION safe_quote(val LONGTEXT) RETURNS LONGTEXT DETERMINISTIC
BEGIN
    IF val IS NULL THEN RETURN 'NULL'; END IF;
    RETURN QUOTE(val);
END //

-- =====================================================
-- Procedure xuất nr_player → bảng staging
-- =====================================================
CREATE PROCEDURE export_nr_player_to_sql()
BEGIN
    DELETE FROM `_export_stage` WHERE `table_name` = 'nr_player';

    INSERT INTO `_export_stage` (`table_name`, `sql_line`)
    SELECT 'nr_player', CONCAT(
        'INSERT INTO `nr_player` VALUES (',
        id, ',', user_id, ',', server_id, ',',
        safe_quote(`name`), ',', gender, ',', class_id, ',', head, ',',
        safe_quote(task), ',', gold, ',', gem, ',', gem_lock, ',',
        safe_quote(item_bag), ',', safe_quote(item_body), ',',
        safe_quote(item_box), ',', safe_quote(box_crack_ball), ',',
        safe_quote(`map`), ',', safe_quote(skill), ',', safe_quote(info), ',',
        clan, ',', safe_quote(shortcut), ',', safe_quote(magic_tree), ',',
        number_cell_bag, ',', number_cell_box, ',',
        safe_quote(friend), ',', safe_quote(enemy), ',',
        ship, ',', fusion, ',', porata, ',',
        safe_quote(item_time), ',', safe_quote(amulet), ',',
        safe_quote(achievement), ',', safe_quote(studying), ',',
        time_played, ',', type_trainning, ',', online, ',',
        time_at_split_fusion, ',', head2, ',', `body`, ',', leg, ',',
        safe_quote(collection_book), ',',
        count_number_of_specialskill_changes, ',',
        safe_quote(special_skill), ',', IFNULL(total_gold_bar, 0), ',',
        safe_quote(reset_time), ',', safe_quote(login_time), ',',
        safe_quote(logout_time), ',', safe_quote(create_time), ',',
        safe_quote(base_name), ',', safe_quote(dataDHVT23), ',',
        thoivang, ',', safe_quote(drop_item), ',',
        IFNULL(point_bo_mong, 0), ',', IFNULL(count_nhiem_vu_bo_mong, 0), ',',
        IFNULL(last_reset_nv_bo_mong, 0), ',', IFNULL(last_coin_value, 0), ',',
        IFNULL(count_task_completed_today, 0), ',', safe_quote(side_task),
        ');'
    ) FROM nr_player;
END //

-- =====================================================
-- Procedure xuất nr_user → bảng staging
-- =====================================================
CREATE PROCEDURE export_nr_user_to_sql()
BEGIN
    DELETE FROM `_export_stage` WHERE `table_name` = 'nr_user';

    INSERT INTO `_export_stage` (`table_name`, `sql_line`)
    SELECT 'nr_user', CONCAT(
        'INSERT INTO `nr_user` (`id`,`username`,`password`,`status`,`gold_bar`,`coin`,`lock_gold`,`lock_time`,`activated`,`role`,`create_time`,`ip`,`domain`,`nang_dong`) VALUES (',
        id, ',', safe_quote(username), ',', safe_quote(password), ',',
        status, ',', IFNULL(gold_bar, 0), ',', IFNULL(coin, 0), ',',
        safe_quote(lock_gold), ',', safe_quote(lock_time), ',',
        activated, ',', `role`, ',', safe_quote(create_time), ',',
        safe_quote(IFNULL(ip, 'none')), ',', safe_quote(IFNULL(domain, 'none')), ',',
        IFNULL(nang_dong, 0),
        ');'
    ) FROM nr_user;
END //

DELIMITER ;

-- =====================================================
-- Procedure xuất nr_disciple → bảng staging
-- =====================================================
DELIMITER //
CREATE PROCEDURE export_nr_disciple_to_sql()
BEGIN
    DELETE FROM `_export_stage` WHERE `table_name` = 'nr_disciple';

    INSERT INTO `_export_stage` (`table_name`, `sql_line`)
    SELECT 'nr_disciple', CONCAT(
        'INSERT INTO `nr_disciple` VALUES (',
        id, ',',
        safe_quote(`name`), ',',
        safe_quote(item_body), ',',
        safe_quote(skill), ',',
        safe_quote(info), ',',
        planet, ',',
        status, ',',
        skill_opened, ',',
        `type`, ',',
        bonus,
        ');'
    ) FROM nr_disciple;
END //
DELIMITER ;

-- =====================================================
-- Events chạy mỗi 5 phút
-- =====================================================
CREATE EVENT export_nr_player_every_5min
ON SCHEDULE EVERY 5 MINUTE
STARTS CURRENT_TIMESTAMP
ON COMPLETION PRESERVE
ENABLE
DO CALL export_nr_player_to_sql();

CREATE EVENT export_nr_user_every_5min
ON SCHEDULE EVERY 5 MINUTE
STARTS CURRENT_TIMESTAMP + INTERVAL 30 SECOND
ON COMPLETION PRESERVE
ENABLE
DO CALL export_nr_user_to_sql();

CREATE EVENT export_nr_disciple_every_5min
ON SCHEDULE EVERY 5 MINUTE
STARTS CURRENT_TIMESTAMP + INTERVAL 60 SECOND
ON COMPLETION PRESERVE
ENABLE
DO CALL export_nr_disciple_to_sql();
