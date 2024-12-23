package com.springboot.entrename.api.user;

import com.springboot.entrename.api.security.authorization.CheckSecurity;
import com.springboot.entrename.domain.user.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    @CheckSecurity.Protected.canManage
    public UserDto getCurrentUser() {
        var currentUser = userService.getCurrentUser();
        return userAssembler.toCurrentUserResponse(currentUser);
    }

    @PutMapping
    @CheckSecurity.Protected.canManage
    public UserDto updateCurrentUser(@RequestBody @Valid UserDto.Update update) {;
        var updateUser = userService.updateCurrentUser(update);
        return userAssembler.toUserResponse(updateUser);
    }
}
