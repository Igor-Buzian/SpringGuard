package com.example.spring.service;

import com.example.spring.entity.User;
import com.example.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountSecurityService {
    @Value("${security.account.max_failed_attempts}")
    private int max_failed_attempts;
    @Value("${security.account.lock_duration_hours}")
    private byte lock_duration_hours;

    public int getMax_failed_attempts() {
        return max_failed_attempts;
    }

    private final UserRepository userRepository;

    public boolean isAccountLocked(User user) {

        if (user.getLockTime() == null) {
            return false;
        }
        LocalDateTime banLocalDateTime = user.getLockTime().plusHours(lock_duration_hours);
        boolean isLocked = LocalDateTime.now().isAfter(banLocalDateTime);
        if (isLocked) {
            resetFailedAttempts(user);
            return false;
        } else {
            return true;
        }
    }


    @Transactional
    public void IncrementFailedAttempts(User user, boolean isCaptchaValid) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        if (user.getFailedAttempts() >= max_failed_attempts && isCaptchaValid == false) {
            user.setLockTime(LocalDateTime.now().plusHours(lock_duration_hours));
        } else if (isCaptchaValid == true) {
            user.setFailedAttempts(max_failed_attempts -1);
        }
        userRepository.save(user);
    }

    @Transactional
    public void resetFailedAttempts(User user) {
        user.setLockTime(null);
        user.setFailedAttempts(0);
        userRepository.save(user);
    }
}
