package com.project.product_management.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    // 1. Why this DTO? To represent a user's public information without exposing sensitive data like the password.
    private Long id;
    private String username;
    private String email;
    private Set<String> roles; // We'll send back the role names as simple strings.
}