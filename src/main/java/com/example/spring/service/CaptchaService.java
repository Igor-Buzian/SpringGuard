package com.example.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class CaptchaService {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isCaptchaValid(String responseToken) {
        if (responseToken == null || responseToken.isEmpty()) {
            return false;
        }

        // Создаём параметры запроса
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("secret", recaptchaSecret);
        requestData.add("response", responseToken);

        // Формируем HTTP-запрос
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestData, headers);

        try {
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(VERIFY_URL, requestEntity, Map.class);
            Map<String, Object> responseBody = responseEntity.getBody();

            if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
                return true;
            }

        } catch (Exception e) {
            // логгируй ошибку при необходимости
            System.out.println("CAPTCHA verification failed: " + e.getMessage());
        }

        return false;
    }
}
