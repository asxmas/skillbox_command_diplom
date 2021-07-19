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

    DTOWrapper register(UserDto.Request.Register userDto);

    DTOWrapper login(LoginDto loginDto);

    DTOWrapper logout(HttpServletRequest request);

    User getAuthorizedUser();

    DTOWrapper passwordResetEmail(String email, HttpServletRequest request);

    Person getInactivePerson();

    DTOWrapper setPassword(String token, String password);

    DTOWrapper setEmail(String email);

    DTOWrapper setNotification(NotificationCode notificationcode, Boolean enabled);

    String resetPasswordAndGetToken(String link);
}
