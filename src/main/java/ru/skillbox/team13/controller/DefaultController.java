package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class DefaultController {

    private final UserService userService;

    @Value("${server.base_url}")
    private String baseUrl;

    @GetMapping("/login")
    public String index(){
        return "index.html";
    }

    @GetMapping("/api/v1/account/password/reset")
    public String passwordReset(@RequestParam("link") String token, HttpServletRequest request){

        String recoveryToken = userService.resetAndGetToken(token);
        if (recoveryToken == null) return "redirect:" +  baseUrl + "/forgot-expired";
        return "redirect:" + baseUrl + "/change-password/" + recoveryToken;
    }

    @GetMapping("/api/v1/account/register/confirm")
    public String registerConfirm(@RequestParam("link") String token){

        if (!userService.registerConfirm(token)) return "redirect:" +  baseUrl + "/forgot-expired";
        return "redirect:" + baseUrl + "/registration-success";
    }

    @GetMapping("/api/v1/account/email/shift")
    public String emailShift(@RequestParam("link") String token){

        Boolean isEmailShifted = userService.setEmail(token);
        if (!isEmailShifted) return "redirect:" +  baseUrl + "/forgot-expired";
        return "redirect:" + baseUrl + "/change-email-success";
    }
}