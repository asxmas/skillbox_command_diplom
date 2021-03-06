package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Collection;

public class WrapperMapper {

    public static DTOWrapper wrap(Object payload, int count, int offset, int limit, boolean errorAndTimestamp) {
        if (errorAndTimestamp) {
            return DTOWrapper.builder()
                    .error("string")
                    .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                    .total(count)
                    .data(getDataByClass(payload))
                    .offset(offset)
                    .perPage(limit)
                    .build();

        } else return DTOWrapper.builder()
                .total(count)
                .data(getDataByClass(payload))
                .offset(offset)
                .perPage(limit)
                .build();
    }

    public static DTOWrapper wrap(Object payload, boolean errorAndTimestamp) {
        if (errorAndTimestamp) {
            return DTOWrapper.builder()
                    .error("string")
                    .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                    .data(getDataByClass(payload))
                    .build();

        } else return DTOWrapper.builder()
                .data(getDataByClass(payload))
                .build();
    }

    public static DTOWrapper wrapMessage(MessageDTO message) {
        return DTOWrapper.builder().error("string")
                .timestamp(TimeUtil.getTimestamp(LocalDateTime.now()))
                .data(message)
                .build();
    }

    public static DTOWrapper wrapMessage(String message) {
        return wrapMessage(new MessageDTO(message));
    }

    private static Object getDataByClass(Object payload) {
        if (payload instanceof Collection<?>) {
            return ((Collection<?>) payload).toArray();
        } else return payload;
    }
}
