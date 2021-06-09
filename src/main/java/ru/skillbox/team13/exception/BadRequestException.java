package ru.skillbox.team13.exception;

import ru.skillbox.team13.dto.ErrorDto;

public class BadRequestException extends RuntimeException {

    private ErrorDto response;

    public BadRequestException(String error){
        response = new ErrorDto(error);
        }

    public ErrorDto getResponse() {
        return response;
    }
}