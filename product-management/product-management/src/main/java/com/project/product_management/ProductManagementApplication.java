package com.project.product_management;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // Use the log4j2 imports
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.product_management.entity.Role;
import com.project.product_management.entity.User;
import com.project.product_management.repository.RoleRepository;
import com.project.product_management.repository.UserRepository;

@SpringBootApplication
public class ProductManagementApplication {

    // 1. Add a logger instance
    private static final Logger logger = LogManager.getLogger(ProductManagementApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApplication.class, args);
        // 2. Add a log message that will definitely run
        logger.info("Product Management Application has started successfully.");
    }
    
        @Bean
        CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                // Create USER and ADMIN roles if they don't exist
                if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                    logger.info("Creating ROLE_USER");
                    roleRepository.save(new Role("ROLE_USER"));
                }
                if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                    logger.info("Creating ROLE_ADMIN");
                    roleRepository.save(new Role("ROLE_ADMIN"));
                }

                // Always fetch the managed adminRole from DB after possible creation
                if (userRepository.findByUsername("admin").isEmpty()) {
                    logger.info("Creating default ADMIN user");

                    Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
                    User adminUser = new User();
                    adminUser.setUsername("admin");
                    adminUser.setEmail("admin@example.com");
                    adminUser.setPassword(passwordEncoder.encode("admin123"));
                    adminUser.setRoles(Set.of(adminRole));

                    userRepository.save(adminUser);
                }
            };
        }
    }