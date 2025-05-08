package com.example.spring.controller;

import com.example.spring.dto.JwtRequest;
import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private  final AuthService authController;

    @GetMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDtoValues registerDtoValues){
        return authController.createNewUser(registerDtoValues);
    }
    @GetMapping("/authentication")
    public ResponseEntity<?> auth(@RequestBody JwtRequest jwtRequest){
        return authController.authUser(jwtRequest);
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
