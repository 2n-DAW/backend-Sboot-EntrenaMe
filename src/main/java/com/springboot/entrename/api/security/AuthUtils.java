package com.springboot.entrename.api.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    public Long getCurrentUserId() {
        return getUserDetails().getId();
    }

    public String getCurrentUserUsername() {
        return getAuthentication().getName();
    }

    public String getCurrentUserEmail() {
        return getUserDetails().getEmail();
    }

    public Collection<? extends GrantedAuthority> getCurrentUserRole() {
        return getAuthentication().getAuthorities();
    }

    public boolean isAuthenticated() {
        return getAuthentication() != null;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private UserDetailsImpl getUserDetails() {
        return (UserDetailsImpl) getAuthentication().getPrincipal();
    }
}
