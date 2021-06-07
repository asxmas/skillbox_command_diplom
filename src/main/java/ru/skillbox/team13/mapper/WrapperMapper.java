package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DTOWrapper;

import java.time.Instant;
import java.util.List;

public class WrapperMapper {

    public static DTOWrapper wrap(List<?> payload, int count, int offset, int limit) {
        return DTOWrapper.builder()
                .error("string")
                .timestamp(Instant.now().getEpochSecond())
                .total(count)
                .data(payload.toArray())
                .offset(offset)
                .perPage(limit)
                .build();
    }
}
