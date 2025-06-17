package com.project.product_management.repository;

import com.project.product_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // We need a way to find a role by its name (e.g., "ROLE_USER").
    Optional<Role> findByName(String name);
}