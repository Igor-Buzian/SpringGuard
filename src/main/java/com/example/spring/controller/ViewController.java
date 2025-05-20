package com.example.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login(Model model, @Value("${security.login.capthca_count}") byte captchaCount)
    {
        model.addAttribute("captchaThreshold",captchaCount);
        return "login-form";
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }

}
