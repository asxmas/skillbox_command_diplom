package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.*;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Boolean register(UserDto.Request.Register userDto);

    PersonDTO login(LoginDto loginDto);

    Boolean logout(HttpServletRequest request);

    PersonDTO getCurrentUserDto();

    User getAuthorizedUser();

    Boolean codeGenerationAndEmail(String email);

    Boolean setPassword(String token, String password);

    Boolean setEmail(String email);

    DTOWrapper setNotification(SubscribeResponseDto subscribeType);
}
