package com.springboot.entrename.api.comment;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.activity.ActivityDto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id_comment;
    @NotNull
    private String body;
    @NotNull
    private Date date;
    @NotNull
    private UUID id_user;
    @NotNull
    private Long id_activity;
    private String slug_comment;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ActivityDto activity;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentWrapper {
        private List<CommentDto> comments;
        private Number comments_count;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Register {
        @NotNull(message = "El comentario no puede ser nulo")
        private String body;
    }
}
