package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.*;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    DTOWrapper register(UserDto.Request.Register userDto, HttpServletRequest request);

    DTOWrapper login(LoginDto loginDto);

    DTOWrapper logout(HttpServletRequest request);

    User getAuthorizedUser();

    DTOWrapper universalAccountMailLink(String email, String route, HttpServletRequest request);

    DTOWrapper setPassword(String token, String password);

    Boolean setEmail(String token);

    Person getInactivePerson();

    String resetAndGetToken(String link);

    DTOWrapper setNotification(SubscribeResponseDto subscribeType);

    Boolean registerConfirm(String link);
}
