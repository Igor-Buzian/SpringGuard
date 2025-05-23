package com.example.spring.controller;

import com.example.spring.dto.JwtRequest;
import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.service.AuthService;
import com.example.spring.service.CreateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private  final AuthService authService;
    private  final CreateService createNewUser;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDtoValues registerDtoValues, HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "g-recaptcha-response", required = false) String captchaResponse){
        return createNewUser.createNewUser(registerDtoValues, response, request,captchaResponse);
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> auth(JwtRequest jwtRequest, HttpServletResponse response, HttpServletRequest request , @RequestParam(name = "g-recaptcha-response", required = false) String captchaResponse)
    {
        return authService.authUser(jwtRequest, request,response,captchaResponse);
    }
    @GetMapping("/login-attempts")
    public ResponseEntity<?> getLoginAttempts(HttpServletRequest request) {
       return authService.getLoginAttempts(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response)
    {
        return authService.logout(response);
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
