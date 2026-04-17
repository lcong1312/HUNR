package com.ngocrong.util;

import org.apache.log4j.Logger;

import java.time.ZoneId;
import java.util.TimeZone;

public final class VietnamTime {

    private static final Logger logger = Logger.getLogger(VietnamTime.class);
    private static final String PRIMARY_ZONE_ID = "Asia/Ho_Chi_Minh";
    private static final String FALLBACK_ZONE_ID = "Asia/Bangkok";
    private static final ZoneId ZONE_ID = resolveZoneId();
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone(ZONE_ID);

    private VietnamTime() {
    }

    private static ZoneId resolveZoneId() {
        try {
            return ZoneId.of(PRIMARY_ZONE_ID);
        } catch (Exception ex) {
            logger.warn("Timezone " + PRIMARY_ZONE_ID + " unavailable, fallback to " + FALLBACK_ZONE_ID);
            return ZoneId.of(FALLBACK_ZONE_ID);
        }
    }

    public static ZoneId zoneId() {
        return ZONE_ID;
    }

    public static TimeZone timeZone() {
        return TIME_ZONE;
    }
}
