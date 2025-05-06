package entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false,unique = true)
    String username;
    @Column(nullable = false)
    String password;
    @Column(nullable = false,unique = true) @Email
    String email;

}
