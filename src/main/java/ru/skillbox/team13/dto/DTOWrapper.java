package ru.skillbox.team13.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DTOWrapper {

    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    private final Object data;
}
