package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AccountController {

    private final UserService userService;

    @PostMapping("account/register")
    public ResponseEntity<SuccessDto> register(@RequestBody @Valid UserDto.Request.Register registerRequest){
        if (userService.register(registerRequest)) {return ResponseEntity.ok(new SuccessDto());}
        else throw new BadRequestException("registration fails");
    }

    @PutMapping("/account/password/recovery")
    public ResponseEntity<SuccessDto> recovery(@RequestBody LoginDto loginDto, @RequestHeader("origin") String origin){
        if (userService.codeGenerationAndEmail(loginDto.getEmail(), origin)) {return ResponseEntity.ok(new SuccessDto());}
        else throw new BadRequestException("User not registered");
    }
}
