package com.example.spring.service;

import com.example.spring.dto.JwtRequest;
import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.entity.User;
import com.example.spring.exeption.InfoExeption;
import com.example.spring.repository.UserRepository;
import com.example.spring.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserService userService;
    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final  JwtTokenUtils jwtTokenUtils;
   public ResponseEntity<?> createNewUser(@RequestBody RegisterDtoValues registerDtoValues){
       if(userRepository.existsByEmail(registerDtoValues.getEmail())){
           return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "This user is exist!"), HttpStatus.FORBIDDEN);
       }
       User user = new User();
       user.setEmail(registerDtoValues.getEmail());
       user.setPassword(passwordEncoder.encode(registerDtoValues.getPassword()));
       user.setUpdatedAt(LocalDateTime.now());
       user.setUsername(registerDtoValues.getUsername());
       if(!user.getPassword().equals(registerDtoValues.getPassword())){
           return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "Different passwords"), HttpStatus.FORBIDDEN);
       }
        String token = jwtTokenUtils.generateToken(user);
       userRepository.save(user);
       return  ResponseEntity.ok(token);
   }

        public ResponseEntity<?> authUser(@RequestBody JwtRequest jwtRequest)
        {
            if(!userRepository.existsByEmail(jwtRequest.getUsername())){
                return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "This user is exist!"), HttpStatus.FORBIDDEN);
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(), jwtRequest.getPassword()
            );
            User user = userService.loadLogin(jwtRequest.getUsername());
            String token = jwtTokenUtils.generateToken(user);
            return ResponseEntity.ok(token);
        }
}
