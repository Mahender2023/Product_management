package com.project.product_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Lombok: Generates getters, setters, etc.
public class RegisterDto {

    // 1. Why? We reuse our validation knowledge here.
    // The API will reject any registration request that doesn't meet these criteria.
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}