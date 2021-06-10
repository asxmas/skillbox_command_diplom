package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {

    private String token;
    private String email;
    private String password;
}