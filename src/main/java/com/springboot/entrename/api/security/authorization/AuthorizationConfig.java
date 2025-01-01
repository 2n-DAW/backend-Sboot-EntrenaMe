package com.springboot.entrename.api.security.authorization;

import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.comment.CommentService;
import com.springboot.entrename.api.security.AuthUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {
    private final AuthUtils authUtils;
    private final UserService userService;
    private final CommentService commentService;

    public boolean isAuthenticated() {
        var isAuthenticated = authUtils.isAuthenticated();
        return isAuthenticated;
    }

    public boolean isProfileOwner(String username) {
        if (!isAuthenticated()) {
            return false;
        }

        var user = userService.getUserByUsername(username);

        return authenticatedUserEquals(user.getIdUser());
    }

    public boolean isProfileNonOwner(String username) {
        if (!isAuthenticated()) {
            return false;
        }

        var user = userService.getUserByUsername(username);

        return !authenticatedUserEquals(user.getIdUser());
    }

    public boolean isCommentAuthor(String slugComment) {
        if (!isAuthenticated()) {
            return false;
        }

        var comment = commentService.getComment(slugComment);
        var author = comment.getIdUser().getIdUser();

        return authenticatedUserEquals(author);
    }

    public boolean ableBlacklisted() {
        if (!isAuthenticated()) {
            return false;
        }

        var typeUser = authUtils.getCurrentUserRole();
        if (typeUser.toString().contains("admin")) {
            return false;
        }

        return true;
    }

    private boolean authenticatedUserEquals(UUID user) {
        return userService.getCurrentUser().getIdUser().equals(user);
    }
}
