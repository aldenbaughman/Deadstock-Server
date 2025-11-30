package com.Deadstock.demo.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    
    private String firstName;

    private String lastName;
    
    @Column(unique = true)
    private String email;

    private String password;
}
