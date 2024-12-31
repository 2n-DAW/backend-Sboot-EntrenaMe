package com.springboot.entrename.api.comment;

import com.springboot.entrename.domain.comment.CommentEntity;
import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.api.activity.ActivityAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentAssembler {
    private final UserAssembler userAssembler;
    private final ActivityAssembler activityAssembler;

    public CommentDto.CommentWrapper toCommentsList(List<CommentEntity> commentEntities) {
        var content = commentEntities.stream()
            .map(this::toCommentResponse)
            .collect(Collectors.toList());

        return buildResponse(content, commentEntities.size());
    }

    public CommentDto.CommentWrapper toDetailedCommentsList(List<CommentEntity> commentEntities) {
        var content = commentEntities.stream()
            .map(this::toDetailedCommentResponse)
            .collect(Collectors.toList());

        return buildResponse(content, commentEntities.size());
    }

    public CommentDto toCommentResponse(CommentEntity commentEntity) {
        return buildComment(commentEntity, false);
    }

    public CommentDto toDetailedCommentResponse(CommentEntity commentEntity) {
        return buildComment(commentEntity, true);
    }

    private CommentDto buildComment(CommentEntity commentEntity, boolean detailed) {
        CommentDto.CommentDtoBuilder builder = CommentDto.builder()
            .id_comment(commentEntity.getId_comment())
            .body(commentEntity.getBody())
            .date(commentEntity.getDate())
            .id_user(commentEntity.getIdUser().getIdUser())
            .id_activity(commentEntity.getIdActivity().getIdActivity())
            .slug_comment(commentEntity.getSlugComment());

        if (detailed) {
            builder
                .user(userAssembler.toUserWithoutPassResponse(commentEntity.getIdUser()))
                .activity(activityAssembler.toActivityResponse(commentEntity.getIdActivity()));
        }

        return builder.build();
    }

    private CommentDto.CommentWrapper buildResponse(List<CommentDto> comments, Number totalComments) {
        return CommentDto.CommentWrapper.builder()
            .comments(comments)
            .comments_count(totalComments) 
            .build();
    }
}
