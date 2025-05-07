package com.example.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class AuthController {

    @GetMapping("/user1")
    public String user(){
        return "user";
    }
    @GetMapping("/admin1")
    public String admin(){
        return "admin";
    }
    @GetMapping("/info1")
    public String info(/*Principal principal*/){
        return "principal.getName()";
    }
}
