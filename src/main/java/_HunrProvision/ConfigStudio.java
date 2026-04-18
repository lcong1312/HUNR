package _HunrProvision;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 *
 */
public class ConfigStudio {

    private static final Properties PROPS = loadProperties();

    public static final String SLOGAN = "Chào mừng bạn đến với Chú bé rồng";
    public static final String ABBREVIATION = "Chú bé rồng";
    public static final String SLOGAN_BOTCOLD = "Chú be rồng";
    public static final String SLOGAN_BOTSOSINH = "Chú bé rồng";
    public static final String SLOGAN_BOTTRAIN = "Chú bé rồng";
    public static final String MESSAGE_LOGIN2 = "Đăng ký tài khoản tại vcong.store2003.online nhé!";
    public static final String MESSAGE_INPUT_CARD = "Bạn hãy truy cập trang: vcong.store2003.online để nạp tiền nhéeeeee!";
    public static final String SERVER_VERSION = "0.0.1";
    public static final String WEBSITE_URL = "vcong.store2003.online";

    // ====================================== MODE ADMIN
    public static final boolean MODE_ADMIN = false;
    public static final String MESSAGE_MODE_ADMIN = "Admin đang can thiệp game. thằng nào bug hay tool thì run đi nhé";

    // ====================================== MODE COMING SOON
    public static final boolean MODE_COMINGSOON = false;
    public static final int COMINGSOON_YEAR = 2025;
    public static final int COMINGSOON_MONTH = 8;
    public static final int COMINGSOON_DAY = 31;
    public static final int COMINGSOON_HOUR = 17;
    public static final int COMINGSOON_MINUTE = 30;
    public static final String MESSAGE_COMINGSOON = "Game sắp ra mắt rồi đó. chờ đi nhé";

    // ====================================== MODE MAP NOEL
    public static final boolean MODE_MAP_NOEL = getBoolean("event.map.noel.enabled", false);

    // ====================================== MODE MAP TET
    public static final boolean MODE_MAP_TET = getBoolean("event.map.tet.enabled", false);

    // ====================================== NPC BO MONG
    public static final boolean NPC_BO_MONG = getBoolean("event.bo_mong.enabled", true);

    // ====================================== EVENT NEWYEAR 2026
    public static final boolean EVENT_NEWYEAR_2026 = getBoolean("event.newyear_2026.enabled", false);
    public static final int EVENT_NEWYEAR_2026_HOLD_TIME_MINUTES = getInt("event.newyear_2026.hold_time_minutes", 30);

    // ====================================== LEGACY EVENTS
    public static final boolean EVENT_TET_2025 = getBoolean("event.tet_2025.enabled", false);
    public static final boolean EVENT_DA_NANG_CAP = getBoolean("event.da_nang_cap.enabled", false);
    public static final boolean EVENT_TAM_THANG_BA = getBoolean("event.tam_thang_ba.enabled", false);
    public static final boolean EVENT_SUMMER_BEACH = getBoolean("event.summer_beach.enabled", false);
    public static final boolean EVENT_NUOC_MIA = getBoolean("event.nuoc_mia.enabled", false);
    public static final boolean EVENT_LUA_THAN = getBoolean("event.lua_than.enabled", false);
    public static final boolean EVENT_QUOC_KHANH = getBoolean("event.quoc_khanh.enabled", false);
    public static final boolean EVENT_CAU_CA = getBoolean("event.cau_ca.enabled", false);

    // ====================================== OSIN CHECK-IN
    public static final boolean EVENT_OSIN_CHECKIN = getBoolean("event.osin_checkin.enabled", true);
    public static final int[] EVENT_OSIN_CHECKIN_MILESTONES = getIntArray(
            "event.osin_checkin.milestones",
            new int[]{2000, 1000, 500, 400, 300, 200}
    );
    public static final boolean EVENT_OSIN_CHECKIN_AUTO_REGISTER = getBoolean(
            "event.osin_checkin.auto_register.enabled", true
    );
    public static final int EVENT_OSIN_CHECKIN_AUTO_TARGET_MIN = getInt(
            "event.osin_checkin.auto_register.target_min", 500
    );
    public static final int EVENT_OSIN_CHECKIN_AUTO_TARGET_MAX = getInt(
            "event.osin_checkin.auto_register.target_max", 650
    );
    public static final int EVENT_OSIN_CHECKIN_AUTO_INTERVAL_MIN_MS = getInt(
            "event.osin_checkin.auto_register.interval_min_ms", 2000
    );
    public static final int EVENT_OSIN_CHECKIN_AUTO_INTERVAL_MAX_MS = getInt(
            "event.osin_checkin.auto_register.interval_max_ms", 5000
    );

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = ConfigStudio.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            }
        } catch (Exception ignored) {
        }
        return props;
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        String value = PROPS.getProperty(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value.trim());
    }

    private static int getInt(String key, int defaultValue) {
        String value = PROPS.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }

    private static int[] getIntArray(String key, int[] defaultValue) {
        String value = PROPS.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue.clone();
        }
        try {
            String[] parts = value.split(",");
            int[] result = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                result[i] = Integer.parseInt(parts[i].trim());
            }
            return result;
        } catch (Exception ignored) {
            return defaultValue.clone();
        }
    }

}
