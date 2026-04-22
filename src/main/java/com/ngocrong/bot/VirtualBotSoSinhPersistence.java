package com.ngocrong.bot;

import com.ngocrong.map.MapManager;
import com.ngocrong.map.TMap;
import com.ngocrong.map.tzone.Zone;
import com.ngocrong.server.mysql.MySQLConnect;
import com.ngocrong.user.Player;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class VirtualBotSoSinhPersistence {

    private static final Logger logger = Logger.getLogger(VirtualBotSoSinhPersistence.class);
    private static final String TABLE_NAME = "nr_virtual_bot_sosinh";

    private VirtualBotSoSinhPersistence() {
    }

    public static boolean saveBot(VirtualBot_SoSinh bot) {
        if (bot == null || bot.name == null || bot.name.isEmpty()) {
            return false;
        }
        Connection conn = MySQLConnect.getConnection();
        if (conn == null) {
            return false;
        }
        String json = bot.toPersistentJson();
        if (json == null || json.isEmpty()) {
            return false;
        }
        long now = System.currentTimeMillis();
        try {
            if (updateBot(conn, bot.name, json, now) > 0) {
                return true;
            }
            insertBot(conn, bot.name, json, now);
            return true;
        } catch (SQLException ex) {
            if (isDuplicateKey(ex)) {
                try {
                    return updateBot(conn, bot.name, json, now) > 0;
                } catch (SQLException updateEx) {
                    logger.warn("Failed to update duplicate bot snapshot " + bot.name + ": " + updateEx.getMessage());
                    return false;
                }
            }
            logger.warn("Failed to save bot snapshot " + bot.name + ": " + ex.getMessage());
            return false;
        }
    }

    public static int saveAllActiveBots() {
        int saved = 0;
        for (VirtualBot_SoSinh bot : getActiveBotsSnapshot()) {
            if (saveBot(bot)) {
                saved++;
            }
        }
        return saved;
    }

    public static int restoreAllBots() {
        Connection conn = MySQLConnect.getConnection();
        if (conn == null) {
            return 0;
        }
        Set<String> activeNames = getActiveBotNames();
        int restored = 0;
        String sql = "SELECT bot_name, state_json FROM " + TABLE_NAME + " ORDER BY id ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String botName = rs.getString("bot_name");
                if (botName == null || botName.isEmpty() || activeNames.contains(botName)) {
                    continue;
                }
                String json = rs.getString("state_json");
                if (json == null || json.isEmpty()) {
                    continue;
                }
                VirtualBot_SoSinh bot = new VirtualBot_SoSinh(botName);
                if (!bot.restoreFromPersistentJson(json)) {
                    continue;
                }
                if (!bot.spawnFromPersistedLocation()) {
                    continue;
                }
                restored++;
                activeNames.add(bot.name);
            }
        } catch (SQLException ex) {
            logger.warn("Failed to restore SoSinh bots: " + ex.getMessage());
        }
        return restored;
    }

    public static void deleteBotByName(String botName) {
        if (botName == null || botName.isEmpty()) {
            return;
        }
        Connection conn = MySQLConnect.getConnection();
        if (conn == null) {
            return;
        }
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE bot_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, botName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.warn("Failed to delete bot snapshot " + botName + ": " + ex.getMessage());
        }
    }

    private static int updateBot(Connection conn, String botName, String json, long now) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET state_json = ?, last_seen = ?, updated_at = CURRENT_TIMESTAMP WHERE bot_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, json);
            ps.setLong(2, now);
            ps.setString(3, botName);
            return ps.executeUpdate();
        }
    }

    private static void insertBot(Connection conn, String botName, String json, long now) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (bot_name, state_json, last_seen) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, botName);
            ps.setString(2, json);
            ps.setLong(3, now);
            ps.executeUpdate();
        }
    }

    private static boolean isDuplicateKey(SQLException ex) {
        return ex != null && ex.getMessage() != null && ex.getMessage().contains("Duplicate entry");
    }

    private static Set<String> getActiveBotNames() {
        Set<String> names = new HashSet<>();
        for (VirtualBot_SoSinh bot : getActiveBotsSnapshot()) {
            if (bot != null && bot.name != null && !bot.name.isEmpty()) {
                names.add(bot.name);
            }
        }
        return names;
    }

    private static List<VirtualBot_SoSinh> getActiveBotsSnapshot() {
        List<VirtualBot_SoSinh> bots = new ArrayList<>();
        MapManager mapManager = MapManager.getInstance();
        if (mapManager == null || mapManager.maps == null) {
            return bots;
        }
        for (TMap map : mapManager.maps.values()) {
            if (map == null || map.zones == null) {
                continue;
            }
            synchronized (map.zones) {
                for (Zone zone : map.zones) {
                    if (zone == null) {
                        continue;
                    }
                    List<Player> players = zone.getPlayers();
                    if (players == null) {
                        continue;
                    }
                    synchronized (players) {
                        for (Player player : players) {
                            if (player instanceof VirtualBot_SoSinh) {
                                bots.add((VirtualBot_SoSinh) player);
                            }
                        }
                    }
                }
            }
        }
        return bots;
    }
}
