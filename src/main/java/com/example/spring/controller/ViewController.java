package com.example.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login(Model model, @Value("${security.login.capthca_count}") byte captchaCount, @RequestParam(name = "error", required = false)String error,@RequestParam(name = "attempt",required = false)Byte attempt)
    {
        model.addAttribute("captchaThreshold",captchaCount);
        if(error != null){
            String errorMessage = switch (error)
            {
                case "ip_banned" -> "You is too much try to enter in this cite so you have ban for few time";
                case "incorrect" -> "Incorrect login or password "+ (attempt !=null ? attempt + " /3":"");
                case "account_banned" -> "Your account was banned";
                default -> "Authentication error";
            };
            model.addAttribute("errorMessage",errorMessage);
        }
        return "login-form";
    }
    @GetMapping("/register")
    public String register(Model model, @RequestParam(name = "attempts", required = false) Byte attempts, @RequestParam(name = "error",required = false)String error)
    {
        if(error!=null)
        {
            String errorMessage = switch (error){
                case "exist_mail" -> "This mail is exist. Please, Try Again!";
                case  "ip_banned" -> "You have a ban for few time. Please, wait!";
                case "different_password" -> "You have different passwords!";
                default -> "Register error";
            };
        }
        return "register-form";
    }
    {

    }

    @GetMapping("/success")
    public String success()
    {
        return "success";
    }

}
