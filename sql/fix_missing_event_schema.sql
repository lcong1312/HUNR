-- Đồng bộ schema tối thiểu cho event NewYear2026 + Osin Lì xì
-- Chạy trên DB hunr

-- 1) Cột sự kiện trong bảng top siêu hạng
ALTER TABLE `nr_super_rank`
    ADD COLUMN IF NOT EXISTS `event_newyear2026` LONGTEXT NULL;

-- 2) Bảng lưu lịch sử rút lì xì mỗi ngày
CREATE TABLE IF NOT EXISTS `nr_event_osin_lixi` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `player_id` INT NOT NULL,
    `lixi_date` DATETIME(3) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_osin_lixi_player_date` (`player_id`, `lixi_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
