package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.NotificationType;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    Boolean register(UserDto.Request.Register userDto);

    PersonDTO login(LoginDto loginDto);

    Boolean logout(HttpServletRequest request);

    PersonDTO getCurrentUserDto();

    User getAuthorizedUser();

    Boolean codeGenerationAndEmail(String email);

    Boolean setPassword(String token, String password);

    Boolean setEmail(String email);

    Boolean setNotification(NotificationCode notificationcode, Boolean enabled);
}
