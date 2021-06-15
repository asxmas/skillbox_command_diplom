package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.util.TimeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
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

    public static DTOWrapper wrapSingleData(Object payload) {
        return DTOWrapper.builder()
                .data(payload)
                .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                .error("string")
                .build();
    }


    public static DTOWrapper wrap(List<?> payload) {
            return DTOWrapper.builder()
                    .error("string")
                    .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                    .data(payload.toArray())
                    .build();
        }

        public static DTOWrapper wrapDataOnly(List<?> payload) {
            return DTOWrapper.builder().data(payload.toArray()).build();
        }

        public static DTOWrapper wrapMessage(MessageDTO message) {
            return DTOWrapper.builder().error("string")
                    .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                    .data(message)
                    .build();
        }
    }
