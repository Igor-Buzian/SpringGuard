package com.example.spring.service;

import com.example.spring.entity.User;
import com.example.spring.exeption.InfoExeption;
import com.example.spring.repository.RoleRepository;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

        public User loadLogin(String login){

            return userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Not found user with name "+login));
        }
    public String loadRole(String roleName){
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new UsernameNotFoundException("Role not found with name: " + roleName))
                .getName();
    }

}
