package com.springboot.entrename.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserEmail() {
        return getAuthentication().getName();
    }

    // public String getCurrentUserToken() {
    //     var authentication = getAuthentication();
    //     if (authentication != null && authentication.getCredentials() instanceof String) {
    //         return (String) authentication.getCredentials();
    //     }
    
    //     return null;
    // }

    public boolean isAuthenticated() {
        return getAuthentication() != null;
    }
}
