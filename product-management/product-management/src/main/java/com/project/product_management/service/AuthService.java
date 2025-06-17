package com.project.product_management.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.product_management.dto.LoginDto;
import com.project.product_management.dto.RegisterDto;
import com.project.product_management.entity.Role;
import com.project.product_management.entity.User;
import com.project.product_management.repository.RoleRepository;
import com.project.product_management.repository.UserRepository;
import com.project.product_management.security.JwtTokenProvider;
import com.project.product_management.exception.UserAlreadyExistsException;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public String registerUser(RegisterDto registerDto) {
        // Business Logic: Check if username already exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            // THROW our new specific exception
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        // Business Logic: Check if email already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            // THROW our new specific exception
            throw new UserAlreadyExistsException("Email is already in use!");
        }

        // ... (rest of the method is unchanged)
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        return "User registered successfully!";
    }

    // ... (login method is unchanged)
}