package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/")
public class AccountController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<DTOWrapper> register(@RequestBody @Valid UserDto.Request.Register registerRequest, HttpServletRequest request){
        return ResponseEntity.ok(userService.register(registerRequest, request));
    }

    //отправка ссылки на сброс пароля
    @PutMapping("password/recovery")
    public ResponseEntity<DTOWrapper> recovery(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request){
        return ResponseEntity.ok(userService.universalAccountMailLink(loginDto.getEmail(), "password/reset", request));
    }

    //отправка ссылки на запланированную смену пароля
    @PutMapping("password/shift")
    public ResponseEntity<DTOWrapper> shiftPassword(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request){
        return ResponseEntity.ok(userService.universalAccountMailLink(loginDto.getEmail(), "password/shift", request));
    }

    //отправка ссылки на запланированную смену почты
    @PutMapping("email/shift")
    public ResponseEntity<DTOWrapper> shiftEmail(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request){
        return ResponseEntity.ok(userService.universalAccountMailLink(loginDto.getEmail(), "email/shift", request));
    }

    @PutMapping("password/set")
    public ResponseEntity<DTOWrapper> setPassword(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(userService.setPassword(loginDto.getToken(), loginDto.getPassword()));
    }

    @PutMapping("notifications")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<DTOWrapper> setNotifications(@RequestBody LoginDto.Notification notificationDto){
        return ResponseEntity.ok(userService.setNotification(notificationDto.getNotificationCode(), notificationDto.isEnable()));
    }
}
