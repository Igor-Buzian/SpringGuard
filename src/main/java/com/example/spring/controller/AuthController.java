package com.example.spring.controller;

import com.example.spring.dto.JwtRequest;
import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.service.AuthService;
import com.example.spring.service.CreateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private  final AuthService authController;
    private  final CreateService createNewUser;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDtoValues registerDtoValues){
        return createNewUser.createNewUser(registerDtoValues);
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> auth(@RequestBody JwtRequest jwtRequest, HttpServletRequest request){
        return authController.authUser(jwtRequest, request);
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
