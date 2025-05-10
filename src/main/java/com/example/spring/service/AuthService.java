package com.example.spring.service;

import com.example.spring.dto.JwtRequest;
import com.example.spring.entity.User;
import com.example.spring.exeption.InfoExeption;
import com.example.spring.repository.UserRepository;
import com.example.spring.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> authUser(@RequestBody JwtRequest jwtRequest) {

        User user = userService.loadLogin(jwtRequest.getEmail());

        if(!passwordEncoder.matches(user.getPassword(), jwtRequest.getPassword())){
            return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "Password is Incorrect!"), HttpStatus.FORBIDDEN);
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                jwtRequest.getEmail(), jwtRequest.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenUtils.generateToken(user);
        return ResponseEntity.ok(token);
    }
}
