package ru.skillbox.team13.util;

import java.time.Instant;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TimeUtil {

    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static long getTimestamp(LocalDateTime ldt) {
        if (ldt == null) {
            return 0;
        }

        return ldt.toInstant(ZONE_OFFSET).toEpochMilli();
    }
    public static LocalDateTime toLocalDateTime (long number)   {
        return LocalDateTime.ofInstant
                (Instant.ofEpochMilli(number), TimeZone.getDefault().toZoneId());
    }
    public static LocalDateTime toLocalDateTime (Timestamp timestamp)   {
        return timestamp.toLocalDateTime();
    }


    public static LocalDateTime getTime(Long timestamp) {
        if (timestamp == null) return null;
        if (timestamp == 0L) return LocalDateTime.now();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_OFFSET);
    }
}
