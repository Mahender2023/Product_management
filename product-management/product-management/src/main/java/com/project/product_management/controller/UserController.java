package com.project.product_management.controller;

import com.project.product_management.dto.UserDto;
import com.project.product_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint for an admin to get a list of all users.
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // 1. Secured: Only admins can access this.
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint for an admin to search for a user by username.
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')") // 2. Secured: Only admins can search for other users.
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    // Endpoint for a logged-in user to get their own profile.
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // 3. Secured: Any logged-in user can access this.
    public ResponseEntity<UserDto> getCurrentUserProfile(Authentication authentication) {
        // 4. Why Authentication object? Spring Security provides this object,
        // which holds the details of the currently authenticated user, including their name (username).
        String username = authentication.getName();
        UserDto userDto = userService.getCurrentUserProfile(username);
        return ResponseEntity.ok(userDto);
    }
}