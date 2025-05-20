package com.example.spring.confing;

import com.example.spring.entity.User;
import com.example.spring.service.UserService;
import com.example.spring.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> authTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "New_User".equals(cookie.getName()))
                    .findFirst();
            return authTokenCookie.map(Cookie::getValue).orElse(null);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if (token != null && jwtTokenUtils.validateToken(token)) {
            try { // Добавим блок try-catch для обработки исключений
                String currentUserEmail = jwtTokenUtils.getLogin(token);
                User user = userService.loadLogin(currentUserEmail);
                   String roleName = jwtTokenUtils.getRoleFromToken(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null,

                        Collections.singleton(new SimpleGrantedAuthority(roleName))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Логируем ошибку и продолжаем цепочку фильтров.  Важно не прерывать обработку!
                e.printStackTrace(); // Выводим в консоль для отладки
                // Можно добавить:  logger.error("Error during authentication: {}", e.getMessage());
            }
        }
        filterChain.doFilter(request, response); // **Обязательно вызываем filterChain.doFilter()**
    }
}
