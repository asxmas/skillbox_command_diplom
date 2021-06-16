package ru.skillbox.team13.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.skillbox.team13.dto.ErrorDto;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(new ErrorDto("Unauthorized"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
    }


    //обработка исключений валидации полей DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException e) {
            Map<String, String> errors = new HashMap<>();
            e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("IN handleValidationExceptions - {}", errors);
        return new ResponseEntity<>(new ErrorDto(errors.toString()), HttpStatus.BAD_REQUEST);
    }
}