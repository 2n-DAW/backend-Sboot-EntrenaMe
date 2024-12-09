package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.user.UserEntity;

public interface ProfileService {
    UserEntity getProfile(String username);

    // UserEntity updateCurrentUser(final UserDto.Update update);
}
