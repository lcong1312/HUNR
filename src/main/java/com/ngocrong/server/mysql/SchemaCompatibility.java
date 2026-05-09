package com.ngocrong.server.mysql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SchemaCompatibility {

    private static final Logger logger = Logger.getLogger(SchemaCompatibility.class);

    private SchemaCompatibility() {
    }

    public static void ensureMinimumSchema(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            ensureNrPlayerColumns(connection);
            ensureNrUserColumns(connection);
            ensureNrSuperRankEventColumn(connection);
            ensureOsinLixiTable(connection);
            ensureBoMongSchema(connection);
            ensureVirtualBotSoSinhTable(connection);
        } catch (SQLException ex) {
            logger.warn("Skip schema compatibility bootstrap: " + ex.getMessage());
        }
    }

    private static void ensureNrPlayerColumns(Connection connection) throws SQLException {
        ensureColumn(connection, "nr_player", "point_bo_mong", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "count_nhiem_vu_bo_mong", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "last_reset_nv_bo_mong", "BIGINT DEFAULT 0");
        ensureColumn(connection, "nr_player", "boss_gold_bar_kill_count", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "boss_gold_bar_reward_mask", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "last_coin_value", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "count_task_completed_today", "INT DEFAULT 0");
        ensureColumn(connection, "nr_player", "side_task", "TEXT NULL");
    }

    private static void ensureNrUserColumns(Connection connection) throws SQLException {
        ensureColumn(connection, "nr_user", "nang_dong", "INT DEFAULT 0");
    }

    private static void ensureNrSuperRankEventColumn(Connection connection) throws SQLException {
        ensureColumn(connection, "nr_super_rank", "event_newyear2026", "LONGTEXT NULL");
    }

    private static void ensureOsinLixiTable(Connection connection) throws SQLException {
        if (tableExists(connection, "nr_event_osin_lixi")) {
            return;
        }
        execute(connection,
                "CREATE TABLE `nr_event_osin_lixi` ("
                        + "`id` INT NOT NULL AUTO_INCREMENT,"
                        + "`player_id` INT NOT NULL,"
                        + "`lixi_date` DATETIME(3) NOT NULL,"
                        + "PRIMARY KEY (`id`),"
                        + "KEY `idx_osin_lixi_player_date` (`player_id`, `lixi_date`)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci");
        logger.info("Created missing table nr_event_osin_lixi");
    }

    private static void ensureBoMongSchema(Connection connection) throws SQLException {
        if (!tableExists(connection, "nr_bo_mong_nhiem_vu")) {
            return;
        }
        ensureColumn(connection, "nr_bo_mong_nhiem_vu", "expired_at", "BIGINT DEFAULT NULL");
    }

    private static void ensureVirtualBotSoSinhTable(Connection connection) throws SQLException {
        if (!tableExists(connection, "nr_virtual_bot_sosinh")) {
            execute(connection,
                    "CREATE TABLE `nr_virtual_bot_sosinh` ("
                            + "`id` BIGINT NOT NULL AUTO_INCREMENT,"
                            + "`bot_name` VARCHAR(100) NOT NULL,"
                            + "`state_json` LONGTEXT NOT NULL,"
                            + "`last_seen` BIGINT NOT NULL DEFAULT 0,"
                            + "`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                            + "`updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                            + "PRIMARY KEY (`id`),"
                            + "UNIQUE KEY `uk_nr_virtual_bot_sosinh_name` (`bot_name`)"
                            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci");
            logger.info("Created missing table nr_virtual_bot_sosinh");
            return;
        }
        ensureColumn(connection, "nr_virtual_bot_sosinh", "bot_name", "VARCHAR(100) NOT NULL");
        ensureColumn(connection, "nr_virtual_bot_sosinh", "state_json", "LONGTEXT NOT NULL");
        ensureColumn(connection, "nr_virtual_bot_sosinh", "last_seen", "BIGINT NOT NULL DEFAULT 0");
        ensureColumn(connection, "nr_virtual_bot_sosinh", "created_at", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP");
        ensureColumn(connection, "nr_virtual_bot_sosinh", "updated_at", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
    }

    private static void ensureColumn(Connection connection, String tableName, String columnName, String ddl) throws SQLException {
        if (!tableExists(connection, tableName)) {
            return;
        }
        if (columnExists(connection, tableName, columnName)) {
            return;
        }
        execute(connection, "ALTER TABLE `" + tableName + "` ADD COLUMN `" + columnName + "` " + ddl);
        logger.info("Added missing column " + tableName + "." + columnName);
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getTables(connection.getCatalog(), null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }

    private static boolean columnExists(Connection connection, String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getColumns(connection.getCatalog(), null, tableName, columnName)) {
            return rs.next();
        }
    }

    private static void execute(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
