package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DTOWrapper {

    private String error;
    private long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int total;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int offset;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int perPage;
    private final Object data;
}
