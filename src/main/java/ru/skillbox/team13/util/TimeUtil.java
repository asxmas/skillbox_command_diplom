package ru.skillbox.team13.util;

import java.time.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.TimeZone;

public class TimeUtil {

    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getTimestamp(LocalDateTime ldt) {
        if (ldt == null) {
            return 0;
        }

        return ldt.toInstant(ZONE_OFFSET).toEpochMilli();
    }


    public static LocalDateTime getTime(Long timestamp) {
        if (timestamp == null) return null;
        if (timestamp == 0L) return LocalDateTime.now();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_OFFSET);
    }

    public static LocalDateTime getBirthday(Integer age) {
        return LocalDateTime.now().minusYears(age);
    }
}
