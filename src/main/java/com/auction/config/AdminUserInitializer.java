package com.auction.config;

import com.auction.model.User;
import com.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@auction.com");
            admin.setPassword(passwordEncoder.encode("1234")); // ✅ encoded password
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin user created");
        }
    }
}
