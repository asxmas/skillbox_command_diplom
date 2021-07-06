package ru.skillbox.team13.dto;

import lombok.Getter;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
public class SuccessDto {

    private String error = "string";
    private Long timestamp = Instant.now().getEpochSecond();
    private Object data;

    public SuccessDto() {
        data = Map.of("message", "ok");
    }

    public SuccessDto(Object object){
        data = object;
    }
}
