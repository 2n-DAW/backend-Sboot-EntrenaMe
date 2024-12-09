package com.springboot.entrename.api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.entrename.api.security.AuthUtils;
import com.springboot.entrename.api.security.UserDetailsServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final AuthUtils authUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String TOKEN_PREFIX = "Bearer ";
        final String token;
        final String email;
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(TOKEN_PREFIX.length());
        // System.out.println("Token ========================================================\n" + token);

        email = jwtUtils.getEmailFromJWT(token);
        // System.out.println("Email ========================================================\n" + email);

        if (email != null && !isAuthenticated()) {
            // System.out.println("isAuthenticated ========================================================\n" + isAuthenticated());

            var userDetails = userDetailsServiceImpl.loadUserByUsername(email);
            // System.out.println("userDetails ========================================================\n" + userDetails);

            // Si el token es válido y el usuario existe, establece la autenticación
            if (jwtUtils.validateJWT(token) && userDetails.getUsername() != null) {
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticated() {
        return authUtils.isAuthenticated();
    }
}
