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
    public ResponseEntity<DTOWrapper> register(@RequestBody @Valid UserDto.Request.Register registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PutMapping("password/recovery")
    public ResponseEntity<DTOWrapper> recovery(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request){
        return ResponseEntity.ok(userService.passwordResetEmail(loginDto.getEmail(), request));
    }

    @PutMapping("password/set")
    public ResponseEntity<DTOWrapper> setPassword(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(userService.setPassword(loginDto.getToken(), loginDto.getPassword()));
    }

    @PutMapping("email")
    public ResponseEntity<DTOWrapper> setEmail(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(userService.setEmail(loginDto.getEmail()));
    }

    @PutMapping("notifications")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<DTOWrapper> setNotifications(@RequestBody LoginDto.Notification notificationDto){
        return ResponseEntity.ok(userService.setNotification(notificationDto.getNotificationCode(), notificationDto.isEnable()));
    }
}
