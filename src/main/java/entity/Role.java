package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;
    @Column(nullable = false,unique = true)
    String name;
}
