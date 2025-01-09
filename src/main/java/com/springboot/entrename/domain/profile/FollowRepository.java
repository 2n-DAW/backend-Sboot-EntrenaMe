package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long>, JpaSpecificationExecutor<FollowEntity> {
    Optional<FollowEntity> findByIdFollowerAndIdFollowed(UserEntity follower, UserEntity followed);
}