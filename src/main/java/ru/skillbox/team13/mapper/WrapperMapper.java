package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.util.TimeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class WrapperMapper {

    public static DTOWrapper wrap(List<?> payload, int count, int offset, int limit) {

        List<String> strings = List.of("one", "two", "three");
        strings.stream().map(s -> s.toLowerCase(Locale.ROOT)).collect(Collectors.toList());

        return DTOWrapper.builder()
                .error("string")
                .timestamp(Instant.now().getEpochSecond())
                .total(count)
                .data(payload.toArray())
                .offset(offset)
                .perPage(limit)
                .build();
    }

    public static DTOWrapper wrap(List<?> payload) {
        return DTOWrapper.builder()
                .error("string")
                .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                .data(payload.toArray())
                .build();
    }

    public static DTOWrapper onlyData(List<?> payload) {
        return DTOWrapper.builder().data(payload.toArray()).build();
    }
}
