package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.user.UserEntity;

import java.util.List;

public interface ProfileService {
    UserEntity getProfile(final String username);

    List<UserEntity> getFollowings(final String username);

    List<UserEntity> getFollowers(final String username);

    UserEntity followUser(final String username);

    UserEntity unfollowUser(final String username);

    boolean isFollowed(final String username);
}
