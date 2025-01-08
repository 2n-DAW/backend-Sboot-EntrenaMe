package com.springboot.entrename.api.security.authorization;

import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.comment.CommentService;
import com.springboot.entrename.domain.inscription.InscriptionService;
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
    private final InscriptionService inscriptionService;

    public boolean isAuthenticated() {
        return authUtils.isAuthenticated();
    }

    public boolean isProfileOwner(String username) {
        if (!isAuthenticated()) return false;
        var user = userService.getUserByUsername(username);
        return authenticatedUserEquals(user.getIdUser());
    }

    public boolean isProfileNonOwner(String username) {
        if (!isAuthenticated()) return false;
        var user = userService.getUserByUsername(username);
        return !authenticatedUserEquals(user.getIdUser());
    }

    public boolean isCommentAuthor(String slugComment) {
        if (!isAuthenticated()) return false;

        var comment = commentService.getComment(slugComment);
        var author = comment.getIdUser().getIdUser();

        return authenticatedUserEquals(author);
    }

    public boolean ableBlacklisted() {
        if (!isAuthenticated()) return false;

        var typeUser = authUtils.getCurrentUserRole();
        if (typeUser.toString().contains("admin")) return false;

        return true;
    }

    public boolean ableModifyInscription(String slugInscription) {
        if (!isAuthenticated()) return false;

        var typeUser = authUtils.getCurrentUserRole();
        if (typeUser.toString().contains("client")) return false;
        if (typeUser.toString().contains("instructor")) {
            var inscription = inscriptionService.getInscription(slugInscription);
            var activityInstructor = inscription.getIdActivity().getIdUserInstructor().getIdUser();
    
            return authenticatedUserEquals(activityInstructor);
        }

        return true;
    }

    private boolean authenticatedUserEquals(UUID user) {
        return userService.getCurrentUser().getIdUser().equals(user);
    }
}
