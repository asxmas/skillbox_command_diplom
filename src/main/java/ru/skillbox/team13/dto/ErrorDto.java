package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Value;

@Getter
public class ErrorDto {

    private String error = "invalid_request";
    @JsonProperty("error_description")
    private String errorDescription;

    public ErrorDto(String errorDescription){
        this.errorDescription = errorDescription;
    }

    public ErrorDto() {
        errorDescription = "string";
    }
}