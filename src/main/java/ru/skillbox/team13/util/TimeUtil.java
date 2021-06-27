package ru.skillbox.team13.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {

    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getTimestamp(LocalDateTime ldt) {
        if (ldt == null) {
            return 0;
        }

        return ldt.toInstant(ZONE_OFFSET).toEpochMilli();
    }

    public static LocalDateTime getTime(long timestamp) {
        if (timestamp == 0L) return LocalDateTime.now();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_OFFSET);
    }
}
