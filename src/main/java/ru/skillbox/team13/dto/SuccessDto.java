package ru.skillbox.team13.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Deprecated
public class SuccessDto {

    private String error = "string";
    private Long timestamp = Instant.now().toEpochMilli();
    private Object data;

    public SuccessDto() {
        data = Map.of("message", "ok");
    }

    public SuccessDto(Object object){
        data = object;
    }
}
