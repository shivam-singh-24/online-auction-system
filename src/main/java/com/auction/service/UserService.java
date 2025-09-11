package com.auction.service;

import com.auction.model.User;
import com.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register new user
    public User register(User user) {
        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("BIDDER");
        }
        return userRepository.save(user);
    }

    // ✅ Login user by username and password
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // ✅ Check if email already exists
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // ✅ Optional: check if username already exists
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
