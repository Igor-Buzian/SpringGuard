package com.example.spring.confing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public String getToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if(authHeader ==null || !authHeader.startsWith("Bearer ")){
            return null;
        }
        return authHeader.substring(7);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        //String user = request
    }
}
