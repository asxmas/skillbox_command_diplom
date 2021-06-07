package ru.skillbox.team13.service;

import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;

import java.time.Instant;
import java.util.List;

@Service
public class DTOWrapperService {

    public DTOWrapper wrap(List<?> payload, int count, int offset, int limit) {
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
