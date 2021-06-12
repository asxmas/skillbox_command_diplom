package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("auth/login")
    public ResponseEntity<SuccessDto> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }
        catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
        return ResponseEntity.ok(new SuccessDto(userService.login(loginDto)));
    }

    @PostMapping("auth/logout")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<SuccessDto> logout(HttpServletRequest request) {
        if (userService.logout(request)) {return ResponseEntity.ok(new SuccessDto());}
        else throw new BadRequestException("Logout fails");
    }
}
