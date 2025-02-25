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
    @CheckSecurity.Public.canRead
    public UserDto register(@RequestBody @Valid UserDto.Register register) {
        // logger.debug("Received registration request: {}", register); // Para debugear en consola
        var user = authService.register(register);
        // logger.debug("User registered successfully: {}", user); // Para debugear en consola
        return userAssembler.toUserResponse(user);
    }

    @PostMapping("/login")
    @CheckSecurity.Public.canRead
    public UserDto login(@RequestBody @Valid UserDto.Login login) {
        String typeUser = getTypeUser(login.getEmail());
        // System.out.println("TypeUser ========================================================\n" + typeUser);

        UserDto userWithToken  = "admin".equals(typeUser) ? laravelLogin(login) : springbootLogin(login);
        return userWithToken;
    }

    @PostMapping("/logout")
    @CheckSecurity.Logout.canBlacklisted
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

    private String getTypeUser(String email) {
        UserEntity user = authService.getTypeUser(email);
        return user.getTypeUser().name();
    }

    private UserDto springbootLogin(@RequestBody @Valid UserDto.Login login) {
        var userWithToken  = authService.springbootLogin(login);
        return userWithToken;
    }

    private UserDto laravelLogin(@RequestBody @Valid UserDto.Login login) {
        var userWithToken  = authService.laravelLogin(login);
        return userWithToken;
    }
}
