package com.example.spring.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {
   @Value("${jwt.secret}")
    private String secret;

   @Value("${jwt.expiration}")
    private Long expiration;


}
