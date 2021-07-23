package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    DTOWrapper register(UserDto.Request.Register userDto, HttpServletRequest request);

    DTOWrapper login(LoginDto loginDto);

    DTOWrapper logout(HttpServletRequest request);

    User getAuthorizedUser();

    DTOWrapper universalAccountMailLink(String email, String route, HttpServletRequest request);

    DTOWrapper setPassword(String token, String password);

    Boolean setEmail(String token);

    DTOWrapper setNotification(NotificationCode notificationcode, Boolean enabled);

    Person getInactivePerson();

    String resetAndGetToken(String link);

    Boolean registerConfirm(String link);
}
