package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.dto.SubscribeNotificationDto;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("auth/login")
    public ResponseEntity<DTOWrapper> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }
        catch (AuthenticationException e) {
            throw new BadRequestException("Неверный логин или пароль");
        }
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @PostMapping("auth/logout")
    public ResponseEntity<DTOWrapper> logout(HttpServletRequest request) {
        return ResponseEntity.ok(userService.logout(request));
    }
}
