package com.example.spring.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false,unique = true, length = 100)
    String username;
    @Column(nullable = false,length = 100)
    String password;
    @Column(nullable = false,unique = true) @Email
    String email;
    @ManyToMany
    @JoinTable(
        name = "user_roles",
           joinColumns =  @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> role;
}
