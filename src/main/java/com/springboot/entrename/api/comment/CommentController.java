package com.springboot.entrename.api.comment;

import com.springboot.entrename.domain.comment.CommentService;

import com.springboot.entrename.api.security.authorization.CheckSecurity;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;

    @GetMapping("/comments")
    public CommentDto.CommentWrapper getAllComments() {
        var comments = commentService.getAllComments();
        return commentAssembler.toCommentsList(comments);
    }

    @GetMapping("/comments/detailed")
    public CommentDto.CommentWrapper getAllDetailedComments() {
        var comments = commentService.getAllComments();
        return commentAssembler.toDetailedCommentsList(comments);
    }

    @GetMapping("/{slugActivity}/comments")
    public CommentDto.CommentWrapper getAllCommentsByActivity(@PathVariable String slugActivity) {
        var comments = commentService.getAllCommentsByActivity(slugActivity);
        return commentAssembler.toCommentsList(comments);
    }

    @GetMapping("/comments/{slugComment}")
    public CommentDto getComment(@PathVariable String slugComment) {
        var comment = commentService.getComment(slugComment);
        return commentAssembler.toCommentResponse(comment);
    }

    @GetMapping("/comments/detailed/{slugComment}")
    public CommentDto getDetailedComment(@PathVariable String slugComment) {
        var comment = commentService.getComment(slugComment);
        return commentAssembler.toDetailedCommentResponse(comment);
    }

    @PostMapping("/{slugActivity}/comments")
    @CheckSecurity.Protected.canManage
    public CommentDto registerComment(@PathVariable String slugActivity, @RequestBody @Valid CommentDto.Register register) {
        var comment = commentService.registerComment(slugActivity, register);
        return commentAssembler.toCommentResponse(comment);
    }

    @DeleteMapping("/{slugActivity}/comments/{slugComment}")
    @CheckSecurity.Comments.canDelete
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable String slugActivity, @PathVariable String slugComment) {
        commentService.deleteComment(slugActivity, slugComment);
        Map<String, String> response = Map.of("message", "Comentario eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}
