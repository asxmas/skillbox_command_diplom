package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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
        if (recoveryToken == null)  {
            log.info("Password reset link expired, redirect to {}", baseUrl + "/forgot-expired");
            return "redirect:" +  baseUrl + "/forgot-expired";
        }
        log.info("Password was cleared, redirect to {}", baseUrl + "/change-password/" + recoveryToken);
        return "redirect:" + baseUrl + "/change-password/" + recoveryToken;
    }

    @GetMapping("/api/v1/account/register/confirm")
    public String registerConfirm(@RequestParam("link") String token){

        if (!userService.registerConfirm(token)) {
            log.info("Register confirm link expired, redirect to {}", baseUrl + "/forgot-expired");
            return "redirect:" +  baseUrl + "/forgot-expired"; }
        log.info("Email confirmed, redirect to {}", baseUrl + "/registration-success");
        return "redirect:" + baseUrl + "/registration-success";
    }

    @GetMapping("/api/v1/account/email/shift")
    public String emailShift(@RequestParam("link") String token){

        Boolean isEmailShifted = userService.setEmail(token);
        if (!isEmailShifted) {
            log.info("Email confirm link expired, redirect to {}", baseUrl + "/forgot-expired");
            return "redirect:" +  baseUrl + "/forgot-expired";
        }
        log.info("Email confirmed, redirect to {}", baseUrl + "/change-email-success");
        return "redirect:" + baseUrl + "/change-email-success";
    }
}