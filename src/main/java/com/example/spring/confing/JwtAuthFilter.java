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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

// Добавляем импорт для логирования (для отладки)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> authTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "Auth_cookie".equals(cookie.getName()))
                    .findFirst();
            return authTokenCookie.map(Cookie::getValue).orElse(null);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 1. Пропускаем фильтр для общедоступных путей, чтобы он не пытался проверять JWT там, где он не нужен.
        // Это КРАЙНЕ ВАЖНО, так как запросы к /login не будут иметь токена изначально.
        if (requestURI.startsWith("/login") || requestURI.startsWith("/register") || requestURI.startsWith("/auth/v1/")) {
            logger.debug("Skipping JwtAuthFilter for public URI: {}", requestURI);
            filterChain.doFilter(request, response);
            return; // Завершаем выполнение фильтра для этих путей
        }

        logger.debug("Processing JwtAuthFilter for protected URI: {}", requestURI);

        String token = getToken(request);
        if (token != null) {
            try {
                if (jwtTokenUtils.validateToken(token)) {
                    String currentUserEmail = jwtTokenUtils.getLogin(token);
                    User user = userService.loadLogin(currentUserEmail);
                    String roleName = jwtTokenUtils.getRoleFromToken(token).get(0);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user, null,
                            Collections.singleton(new SimpleGrantedAuthority(roleName))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("Authentication set for user: {}", currentUserEmail);
                } else {
                    logger.warn("Invalid JWT token for URI: {}", requestURI);
                    // Токен невалиден, но фильтр продолжает работу, чтобы Spring Security
                    // мог обработать это как неаутентифицированный запрос для защищенного ресурса.
                }
            } catch (Exception e) {
                // Логируем ошибку, связанную с обработкой токена (например, user not found, проблемы с JWT-ключом)
                logger.error("Error processing JWT token for URI {}: {}", requestURI, e.getMessage(), e);
                // Важно: не бросать исключение, чтобы Spring Security мог обработать его самостоятельно,
                // либо выдать 401/403 в зависимости от дальнейшей конфигурации.
            }
        } else {
            logger.debug("No JWT token found in cookies for URI: {}", requestURI);
        }

        // Всегда вызываем filterChain.doFilter(), чтобы запрос пошел дальше по цепочке фильтров
        filterChain.doFilter(request, response);
    }
}