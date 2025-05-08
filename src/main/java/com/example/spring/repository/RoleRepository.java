package com.example.spring.repository;

import com.example.spring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleName(String roleName);
}
