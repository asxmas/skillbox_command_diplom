package ru.skillbox.team13.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TimeService {

    private final ZoneOffset zone = ZoneOffset.UTC;

    public long getTimestamp(LocalDateTime ldt) {
        if (ldt == null) {
            return 0;
        }

        return ldt.toEpochSecond(zone);
    }
}
