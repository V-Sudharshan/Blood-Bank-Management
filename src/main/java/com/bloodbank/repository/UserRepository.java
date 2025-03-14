package com.bloodbank.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bloodbank.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}