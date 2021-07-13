package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Boolean register(UserDto.Request.Register userDto);

    PersonDTO login(LoginDto loginDto);

    Boolean logout(HttpServletRequest request);

    User getAuthorizedUser();

    Boolean codeGenerationAndEmail(String email, String origin);

    Person getInactivePerson();

    Boolean setPassword(String token, String password);

    Boolean setEmail(String email);

    Boolean setNotification(NotificationCode notificationcode, Boolean enabled);

    String resetPasswordAndGetToken(String link);
}
