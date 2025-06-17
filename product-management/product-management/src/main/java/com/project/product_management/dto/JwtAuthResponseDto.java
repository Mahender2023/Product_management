package com.project.product_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// 2. Why @NoArgsConstructor? While not strictly needed here, it's good practice
// for DTOs in case they are used in contexts that require a no-argument constructor.
@NoArgsConstructor
public class JwtAuthResponseDto {

    // 3. This field will hold the actual JWT string.
    private String accessToken;

    // 4. It's standard practice in OAuth2 and JWT flows to include the token type.
    // The client should prepend this value (e.g., "Bearer ") to the token when sending it.
    private String tokenType = "Bearer";

    // 5. A convenience constructor to easily create an instance with the access token.
    public JwtAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}