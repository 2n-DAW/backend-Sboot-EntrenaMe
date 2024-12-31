package com.springboot.entrename.domain.comment;

import com.springboot.entrename.api.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentEntity> getAllComments();

    List<CommentEntity> getAllCommentsByActivity(final String slugActivity);

    List<CommentEntity> getAllCommentsByAuthor(final String username);

    CommentEntity getComment(final String slugComment);

    CommentEntity registerComment(final String slugActivity, final CommentDto.Register register);

    void deleteComment(final String slugActivity, final String slugComment);
}
