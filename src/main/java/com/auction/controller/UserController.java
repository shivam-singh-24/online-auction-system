package com.auction.controller;

import com.auction.model.User;
import com.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Login endpoint
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User found = userService.login(user.getUsername(), user.getPassword());
        if (found != null) {
            return found;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    // ✅ Registration endpoint
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // Optional: check if username/email already exists
        if (userService.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        user.setRole("BIDDER"); // Default role
        return userService.register(user);
    }
}
