package ru.skillbox.team13.exception;

import lombok.Value;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Value
public class SuccessResponse {
    /**
     * {
     *   "error": "string",
     *   "timestamp": 1559751301818,
     *   "data": {
     *     "message": "ok"
     *   }
     * }
     */
    String error = "string";
    Long timestamp = LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset());
    Object data;

    public SuccessResponse() {
        data = Map.of("message", "ok");
    }

    public SuccessResponse(Object object){
        data = object;
    }
}
