package com.auction.repository;

import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ For login
    User findByUsername(String username);

    // ✅ For registration duplicate check
    Optional<User> findByEmail(String email);
}
