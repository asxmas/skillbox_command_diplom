package ru.skillbox.team13.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DTOWrapper {

    private final String error;
    private final long timestamp;
    private final int total;
    private final int offset;
    private final int perPage;
    private final Object[] data;
}
