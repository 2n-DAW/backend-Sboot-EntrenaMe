package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    @Override
    public UserEntity getProfile(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> getFollowings(String username) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        List<UserEntity> followings = user.getFollowings().stream()
            .map(FollowEntity::getIdFollowed)
            .collect(Collectors.toList());

        return followings;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> getFollowers(String username) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        List<UserEntity> followers = user.getFollowers().stream()
            .map(FollowEntity::getIdFollower)
            .collect(Collectors.toList());

        return followers;
    }

    @Transactional
    @Override
    public UserEntity followUser(String username) {
        UserEntity follower = userService.getCurrentUser();
        UserEntity followed = userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        followRepository.findByIdFollowerAndIdFollowed(follower, followed).ifPresent(follow -> {
            throw new AppException(Error.ALREADY_FOLLOWED_USER);
        });

        FollowEntity follow = FollowEntity.builder()
            .idFollower(follower)
            .idFollowed(followed)
            .build();
        followRepository.save(follow);

        return followed;
    }

    @Transactional
    @Override
    public UserEntity unfollowUser(String username) {
        UserEntity follower = userService.getCurrentUser();
        UserEntity followed = userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        FollowEntity follow = followRepository.findByIdFollowerAndIdFollowed(follower, followed)
            .orElseThrow(() -> new AppException(Error.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);

        return followed;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFollowed(String username) {
        UserEntity follower = userService.getCurrentUser();
        UserEntity followed = userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        return followRepository.findByIdFollowerAndIdFollowed(follower, followed).isPresent();
    }
}
