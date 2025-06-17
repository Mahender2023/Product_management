package com.project.product_management.service;

import com.project.product_management.dto.UserDto;
import com.project.product_management.entity.User;
import com.project.product_management.exception.ResourceNotFoundException;
import com.project.product_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; // Inject our new mapper

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // --- Admin-specific methods ---

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto) // Use the mapper for each user
                .collect(Collectors.toList());
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toUserDto(user);
    }

    // --- User-specific methods ---

    public UserDto getCurrentUserProfile(String username) {
        // This method finds the user by the username from the JWT token.
        // It's functionally the same as getUserByUsername but named for clarity.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for username: " + username));
        return userMapper.toUserDto(user);
    }
}