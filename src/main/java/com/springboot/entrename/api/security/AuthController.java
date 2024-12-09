package com.springboot.entrename.api.security;

import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.api.user.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

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
        var userWithToken  = authService.login(login);
        return userWithToken;
        
    }
}
