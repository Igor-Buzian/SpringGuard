package com.example.spring.service;

import com.example.spring.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountSecurityService {
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final byte LOCK_DURATION_HOURS = 1;

    public void IncrementFailedAttempts(User user) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        if (user.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
            user.setLockTime(LocalDateTime.now());
        }
    }


    public boolean isAccountLocked(User user) {

        if (user.getLockTime() == null) {
            return false;
        }
        LocalDateTime banLocalDateTime = user.getLockTime().plusHours(LOCK_DURATION_HOURS);
        boolean isLocked = LocalDateTime.now().isAfter(banLocalDateTime);
        if (isLocked) {
            resetFailedAttempts(user);
            return false;
        } else {
            return true;
        }
    }

    public void resetFailedAttempts(User user) {
        user.setLockTime(null);
        user.setFailedAttempts(0);
    }
}
