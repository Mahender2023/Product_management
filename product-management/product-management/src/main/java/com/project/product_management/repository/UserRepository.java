package com.project.product_management.repository;

import com.project.product_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 1. Why Optional? It's a modern, safe way to handle cases where a user might not exist.
    // It prevents NullPointerExceptions.
    Optional<User> findByUsername(String username);

    // 2. Why these methods? We need them to check if a username or email is already taken during registration.
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}