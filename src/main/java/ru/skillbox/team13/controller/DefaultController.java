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

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

    @GetMapping("/api/v1/account/password/reset")
    public String passwordReset(@RequestParam("link") String token, HttpServletRequest request){

        String recoveryToken = userService.resetPasswordAndGetToken(token);
        if (recoveryToken == null) return "redirect:" +  baseUrl + "/forgot-expired";
        return "redirect:" + baseUrl + "/change-password/" + recoveryToken;
    }

    //to do сделать универсальными методы получения ссылки на ресет пароля, смену пароля, смену мэйла
    @GetMapping("/api/v1/account/password/shift")
    public String passwordShift(@RequestParam("link") String token, HttpServletRequest request){

//        String recoveryToken = userService.resetPasswordAndGetToken(token);
//        if (recoveryToken == null) return "redirect:" +  baseUrl + "/forgot-expired";
//        return "redirect:" + baseUrl + "/change-password/" + recoveryToken;
        return null;
    }

    //to do сделать универсальными методы получения ссылки на ресет пароля, смену пароля, смену мэйла
    @GetMapping("/api/v1/account/email/shift")
    public String emailShift(@RequestParam("link") String token, HttpServletRequest request){

//        String recoveryToken = userService.resetPasswordAndGetToken(token);
//        if (recoveryToken == null) return "redirect:" +  baseUrl + "/forgot-expired";
//        return "redirect:" + baseUrl + "/change-password/" + recoveryToken;
        return null;
    }


}