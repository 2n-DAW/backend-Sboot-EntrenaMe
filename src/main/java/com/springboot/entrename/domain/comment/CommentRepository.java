package com.springboot.entrename.domain.comment;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor<CommentEntity> {
    List<CommentEntity> findAllByIdActivity(ActivityEntity activity);

    List<CommentEntity> findAllByIdUser(UserEntity idUser);

    Optional<CommentEntity> findBySlugComment(String slug);

    boolean existsBySlugComment(String slug);
}
