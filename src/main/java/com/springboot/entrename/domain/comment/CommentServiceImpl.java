package com.springboot.entrename.domain.comment;

import com.springboot.entrename.api.comment.CommentDto;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.activity.ActivityService;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.github.slugify.Slugify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ActivityService activityService;
    private final Slugify slg;

    @Transactional(readOnly = true)
    @Override
    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentEntity> getAllCommentsByActivity(String slugActivity) {
        ActivityEntity activity = activityService.getActivity(slugActivity);
        return commentRepository.findAllByIdActivity(activity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentEntity> getAllCommentsByAuthor(String username) {
        UserEntity user = userService.getUserByUsername(username);
        return commentRepository.findAllByIdUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentEntity getComment(String slugComment) {
        return commentRepository.findBySlugComment(slugComment)
            .orElseThrow(() -> new AppException(Error.COMMENT_NOT_FOUND));
    }

    @Transactional
    @Override
    public CommentEntity registerComment(String slugActivity, CommentDto.Register register) {
        UserEntity user = userService.getCurrentUser();
        ActivityEntity activity = activityService.getActivity(slugActivity);

        CommentEntity comment = CommentEntity.builder()
            .body(register.getBody())
            .date(Date.valueOf(LocalDate.now()))
            .idUser(user)
            .idActivity(activity)
            .slugComment(generateUniqueSlug(slugActivity, user.getUsername()))
            .build();

        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(String slugActivity, String slugComment) {
        ActivityEntity activity = activityService.getActivity(slugActivity);

        CommentEntity comment = commentRepository.findBySlugComment(slugComment)
            .filter(c -> c.getIdActivity().getIdActivity().equals(activity.getIdActivity()))
            .orElseThrow(() -> new AppException(Error.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }

    private String generateUniqueSlug(String slugActivity, String username) {
        String baseSlug = slg.slugify("comment-" + slugActivity + "-" + username);
        String uniqueSlug = baseSlug;
        int counter = 1;

        while (commentRepository.existsBySlugComment(uniqueSlug)) {
            uniqueSlug = baseSlug + "-" + counter;
            counter++;
        }

        return uniqueSlug;
    }
}
