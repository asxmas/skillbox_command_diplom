package ru.skillbox.team13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.exception.SuccessResponse;
import ru.skillbox.team13.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ApiAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public ApiAuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/account/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid UserDto.Request.Register registerRequest){
        if (userService.register(registerRequest)) {return ResponseEntity.ok(new SuccessResponse());}
        else throw new BadRequestException("registration fails");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }
        catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
        return ResponseEntity.ok(new SuccessResponse(userService.login(loginDto)));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<SuccessResponse> logout(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            if (userService.logout()) {return ResponseEntity.ok(new SuccessResponse());}
            else throw new BadRequestException("Logout fails");
        }
        catch (AuthenticationException e) {
            return ResponseEntity.ok(new SuccessResponse());
        }
    }
}
