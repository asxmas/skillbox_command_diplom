package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.*;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/")
public class AccountController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<SubscribeNotificationDto> register(@RequestBody @Valid UserDto.Request.Register registerRequest){
        if (userService.register(registerRequest)) {return ResponseEntity.ok(new SubscribeNotificationDto());}
        else throw new BadRequestException("registration fails");
    }

    @PutMapping("password/recovery")
    public ResponseEntity<SubscribeNotificationDto> recovery(@RequestBody @Valid LoginDto loginDto){
        if (userService.codeGenerationAndEmail(loginDto.getEmail())) {return ResponseEntity.ok(new SubscribeNotificationDto());}
        else throw new BadRequestException("user not registered");
    }

    @PutMapping("password/set")
    public ResponseEntity<SubscribeNotificationDto> setPassword(@RequestBody @Valid LoginDto loginDto){
        if (userService.setPassword(loginDto.getToken(), loginDto.getPassword())) { return ResponseEntity.ok(new SubscribeNotificationDto()); }
        else throw new BadRequestException("can't change password");
    }

    @PutMapping("email")
    public ResponseEntity<SubscribeNotificationDto> setEmail(@RequestBody @Valid LoginDto loginDto){
        if (userService.setEmail(loginDto.getEmail())) { return ResponseEntity.ok(new SubscribeNotificationDto()); }
        else throw new BadRequestException("can't change email");
    }

    @PutMapping("notifications")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<DTOWrapper> setNotifications(@RequestBody SubscribeResponseDto subscribeType){
        if (!userService.setNotification(subscribeType).equals(null)) {
            return new ResponseEntity<>(userService.setNotification(subscribeType), HttpStatus.OK);
        } else throw new BadRequestException("can't trigger notification");
    }
}
