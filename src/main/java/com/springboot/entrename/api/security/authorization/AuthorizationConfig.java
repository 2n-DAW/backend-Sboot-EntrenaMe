package com.springboot.entrename.api.security.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.springboot.entrename.api.security.AuthUtils;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {
    private final AuthUtils authUtils;

    public boolean isAuthenticated() {
        var isAuthenticated = authUtils.isAuthenticated();
        // System.out.println("isAuthenticated ========================================================\n" + isAuthenticated);
        return isAuthenticated;
    }
}
