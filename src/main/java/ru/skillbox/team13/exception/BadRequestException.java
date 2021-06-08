package ru.skillbox.team13.exception;

public class BadRequestException extends RuntimeException {

    private ErrorResponse response;

    public BadRequestException(String error){
        response = new ErrorResponse(error);
        }

    public ErrorResponse getResponse() {
        return response;
    }
}