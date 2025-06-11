package com.example.purchases.servicies;

import com.example.purchases.entities.User;
import com.example.purchases.exceptions.UserAlreadyExistException;
import com.example.purchases.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        if (user == null) {
            throw new IllegalStateException("User can not be null") ;
        }

        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            throw new UserAlreadyExistException("User already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
