package ru.skillbox.team13.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ErrorResponse {
    /**
     * {
     *   "error": "invalid_request",
     *   "error_description": "string"
     * }
     */

    String error = "invalid_request";
    @JsonProperty("error_description")
    String errorDescription;

    public ErrorResponse (String errorDescription){
        this.errorDescription = errorDescription;
    }
}