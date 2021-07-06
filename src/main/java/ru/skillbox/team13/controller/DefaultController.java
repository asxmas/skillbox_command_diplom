package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class DefaultController {

    private final UserService userService;

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

    @GetMapping("/api/v1/account/password/reset")
    public String passwordReset(@RequestParam("link") String token, HttpServletRequest request){

        String recoveryToken = userService.resetPasswordAndGetToken(token);
        if (recoveryToken == null) return "redirect:" +  request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + "/forgot-expired";
        return "redirect:" +  request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + "/change-password/" + recoveryToken;
    }
}