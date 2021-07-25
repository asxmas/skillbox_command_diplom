package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;

import javax.servlet.http.HttpServletRequest;

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

    DTOWrapper getNotifications();

    Boolean registerConfirm(String link);

    String resetPasswordAndGetToken(String link);

    DTOWrapper getUserActivity(int userId);

    DTOWrapper setUserDialogStatus(int userId, String status);
}
