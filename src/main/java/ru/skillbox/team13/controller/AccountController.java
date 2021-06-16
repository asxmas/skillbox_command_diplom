package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/")
public class AccountController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<SuccessDto> register(@RequestBody @Valid UserDto.Request.Register registerRequest){
        if (userService.register(registerRequest)) {return ResponseEntity.ok(new SuccessDto());}
        else throw new BadRequestException("registration fails");
    }

    @PutMapping("password/recovery")
    public ResponseEntity<SuccessDto> recovery(@RequestBody @Valid LoginDto loginDto){
        if (userService.codeGenerationAndEmail(loginDto.getEmail())) {return ResponseEntity.ok(new SuccessDto());}
        else throw new BadRequestException("user not registered");
    }

    @PutMapping("password/set")
    public ResponseEntity<SuccessDto> setPassword(@RequestBody @Valid LoginDto loginDto){
        if (userService.setPassword(loginDto.getToken(), loginDto.getPassword())) { return ResponseEntity.ok(new SuccessDto()); }
        else throw new BadRequestException("can't change password");
    }

    @PutMapping("email")
    public ResponseEntity<SuccessDto> setEmail(@RequestBody @Valid LoginDto loginDto){
        if (userService.setEmail(loginDto.getEmail())) { return ResponseEntity.ok(new SuccessDto()); }
        else throw new BadRequestException("can't change email");
    }

    @PutMapping("notifications")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<SuccessDto> setNotifications(@RequestBody LoginDto.Notification notificationDto){
        if (userService.setNotification(notificationDto.getNotificationCode(), notificationDto.isEnable()))
        { return ResponseEntity.ok(new SuccessDto()); }
        else throw new BadRequestException("can't trigger notification");
    }
}
