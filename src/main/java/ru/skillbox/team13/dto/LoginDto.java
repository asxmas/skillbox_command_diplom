package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {

    private String token;
    private @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]+") String email;
    private @Size(min=2, max=50) String password;

}