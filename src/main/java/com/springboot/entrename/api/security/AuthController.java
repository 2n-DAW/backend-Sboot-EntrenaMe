package com.springboot.entrename.api.security;

import com.springboot.entrename.api.security.authorization.CheckSecurity;
import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import jakarta.validation.Valid;

// Para debugear en consola
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final UserAssembler userAssembler;
    // private static final Logger logger = LoggerFactory.getLogger(AuthController.class); // Para debugear en consola

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto.Register register) {
        // logger.debug("Received registration request: {}", register); // Para debugear en consola
        var user = authService.register(register);
        // logger.debug("User registered successfully: {}", user); // Para debugear en consola
        return userAssembler.toUserResponse(user);
    }

    @PostMapping("/login")
    public UserDto.UserWithToken login(@RequestBody @Valid UserDto.Login login) {
        String typeUser = getTypeUser(login.getEmail());
        // System.out.println("TypeUser ========================================================\n" + typeUser);

        UserDto.UserWithToken userWithToken  = "client".equals(typeUser) ? clientLogin(login) : laravelLogin(login);
        return userWithToken;
    }

    private String getTypeUser(String email) {
        UserEntity user = authService.getTypeUser(email);
        return user.getType_user().name();
    }

    private UserDto.UserWithToken clientLogin(@RequestBody @Valid UserDto.Login login) {
        var userWithToken  = authService.clientLogin(login);
        return userWithToken;
        
    }

    private UserDto.UserWithToken laravelLogin(@RequestBody @Valid UserDto.Login login) {
        var userWithToken  = authService.laravelLogin(login);
        return userWithToken;
    }

    @PostMapping("/logout")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<Map<String, String>> logout() {
        var blacklistToken = authService.saveBlacklistToken();

        if (blacklistToken != null) {
            Map<String, String> response = Map.of(
                "message", "RefreshToken guardado en la Blacklist"
            );
            return ResponseEntity.ok(response);
        }

        throw new AppException(Error.BLACKLISTED_TOKEN);
    }
}
