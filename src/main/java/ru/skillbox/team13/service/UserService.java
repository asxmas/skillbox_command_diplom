package ru.skillbox.team13.service;


import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    Boolean register(UserDto.Request.Register userDto);

    PersonDTO login(LoginDto loginDto);

    Boolean logout(HttpServletRequest request);

    PersonDTO getCurrentUser();
}
