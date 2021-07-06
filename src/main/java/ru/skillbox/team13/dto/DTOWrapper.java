package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOWrapper {

    private String error;
    private long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private final Object data;
}
