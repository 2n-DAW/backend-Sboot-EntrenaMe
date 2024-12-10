package com.springboot.entrename.api.profile;

import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.profile.ProfileService;
import com.springboot.entrename.api.security.authorization.CheckSecurity;
import com.springboot.entrename.api.security.AuthUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.annotation.Validated;

// import jakarta.validation.Valid;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Validated
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileAssembler profileAssembler;
    private final AuthUtils authUtils;

    @GetMapping("/{username}")
    @CheckSecurity.Public.canRead
    public ProfileDto getProfile(@PathVariable String username, WebRequest request) { // WebRequest: Representa la solicitud HTTP actual permitiendo acceder a encabezados, parámetros...
        var profile = profileService.getProfile(username);
        
        if (authUtils.isAuthenticated()) {
            var currentUser = userService.getCurrentUser();
            if (currentUser.getUsername().equals(username)) {
                return profileAssembler.toProfileResponse(profile);
            };
        }

        return profileAssembler.toPublicProfileResponse(profile);
    }
}
