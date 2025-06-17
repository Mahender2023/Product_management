package com.project.product_management.service;

import com.project.product_management.dto.UserDto;
import com.project.product_management.entity.Role;
import com.project.product_management.entity.User;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service // 1. Why @Service? We make it a Spring bean so we can inject it where needed.
public class UserMapper {

    // 2. This method cleanly converts a User entity to our safe UserDto.
    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getName) // Uses a method reference for clean mapping
                .collect(Collectors.toSet()));
        return userDto;
    }
}