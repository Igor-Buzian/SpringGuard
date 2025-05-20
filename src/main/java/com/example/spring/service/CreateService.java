package com.example.spring.service;

import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.entity.User;
import com.example.spring.exeption.InfoExeption;
import com.example.spring.repository.UserRepository;
import com.example.spring.utils.JwtTokenUtils;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class CreateService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final ValidationService validationService;
    private  final JwtTokenUtils jwtTokenUtils;

    public ResponseEntity<?> createNewUser(@RequestBody RegisterDtoValues registerDtoValues, HttpServletResponse response){
        if(userRepository.existsByEmail(registerDtoValues.getEmail())){
            return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "This user is exist!"), HttpStatus.FORBIDDEN);
        }
        User user = new User();
        user.setEmail(registerDtoValues.getEmail());
        user.setPassword(passwordEncoder.encode(registerDtoValues.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setUsername(registerDtoValues.getUsername());
        //  if(!registerDtoValues.getConfirmPassword().equals(registerDtoValues.getPassword())){
        if(validationService.validationPassword(registerDtoValues.getPassword(), registerDtoValues.getConfirmPassword())){
            return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "Different passwords"), HttpStatus.FORBIDDEN);
        }
        String token = jwtTokenUtils.generateToken(user);
        userRepository.save(user);
        Cookie cookie = new Cookie("New_User", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/success");
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
