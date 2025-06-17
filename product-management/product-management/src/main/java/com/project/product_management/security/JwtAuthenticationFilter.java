package com.project.product_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.project.product_management.security.CustomUserDetailsService;

// 1. Why extends OncePerRequestFilter? To ensure this filter runs only once for each request.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 2. Get JWT token from HTTP request
        String token = getJWTFromRequest(request);

        // 3. Validate token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // 4. Get username from token
            String username = tokenProvider.getUsernameFromJWT(token);

            // 5. Load user associated with token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // 6. Create an authentication object
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 7. Set the authentication object in the SecurityContext
            // This is the crucial step that tells Spring Security "this user is authenticated".
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        
        // 8. Pass the request to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // The header should look like: "Authorization: Bearer <token>"
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}