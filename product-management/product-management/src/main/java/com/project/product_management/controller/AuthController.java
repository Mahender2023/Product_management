package com.project.product_management.controller;

import com.project.product_management.dto.LoginDto;
import com.project.product_management.dto.RegisterDto;
import com.project.product_management.dto.JwtAuthResponseDto; 
import com.project.product_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.product_management.dto.MessageResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    // Change the return type here
    public ResponseEntity<MessageResponseDto> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        String responseMessage = authService.registerUser(registerDto);
        // Return a JSON object with the message
        return new ResponseEntity<>(new MessageResponseDto(responseMessage), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    // THE FIX IS HERE: The method signature now correctly matches the return type.
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @GetMapping("/test-auth")
    public String testAuthenticatedEndpoint() {
        return "You are authenticated! This is a protected endpoint.";
    }
}