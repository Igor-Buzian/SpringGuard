package com.example.spring.service;

import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    public Boolean validationEmail(String currentEmail, String emailInBd){
        return currentEmail.equals(emailInBd);
    }
    public Boolean validationPassword(String currentPassword, String passwordInBd){
        return currentPassword.equals(passwordInBd);
    }

    public Boolean validationRole(String currentRole, String roleInBd){
        return currentRole.equals(roleInBd);
    }
}
