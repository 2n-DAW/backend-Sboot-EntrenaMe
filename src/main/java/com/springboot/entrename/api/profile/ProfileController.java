package com.springboot.entrename.api.profile;

import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.profile.ProfileService;
import com.springboot.entrename.api.comment.CommentDto;
import com.springboot.entrename.domain.comment.CommentService;
import com.springboot.entrename.api.comment.CommentAssembler;
import com.springboot.entrename.api.security.AuthUtils;
import com.springboot.entrename.api.security.authorization.CheckSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Validated
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileAssembler profileAssembler;
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;
    private final AuthUtils authUtils;

    @GetMapping("/{username}")
    @CheckSecurity.Public.canRead
    public ProfileDto getProfile(@PathVariable String username) {
        var profile = profileService.getProfile(username);
        
        if (authUtils.isAuthenticated()) {
            var currentUser = userService.getCurrentUser();
            if (currentUser.getUsername().equals(username)) {
                return profileAssembler.toProfileResponse(profile);
            };
        }

        return profileAssembler.toPublicProfileResponse(profile);
    }

    @GetMapping("/{username}/comments")
    @CheckSecurity.Profile.canManage
    public CommentDto.CommentWrapper getAllCommentsByAuthor(@PathVariable String username) {
        var comments = commentService.getAllCommentsByAuthor(username);
        return commentAssembler.toCommentsList(comments);
    }
}
