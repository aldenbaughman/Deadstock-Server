package com.Deadstock.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Deadstock.demo.repository.AccountRepository;
import com.Deadstock.demo.user.User;

@Service
public class UserService {
    private final AccountRepository repo;
    private final PasswordEncoder encoder;

    public UserService(AccountRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String username, String firstname, String lastname, String email, String password) {
        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username is taken");
        }
        else if (repo.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use");
        }
        else if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")){
            throw new RuntimeException("Password is Missing an Uppercase Character, Number, or Special Character");
        }

        User u = new User();
        u.setUsername(username);
        u.setFirstName(firstname);
        u.setLastName(lastname);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));

        return repo.save(u);
    }
}
