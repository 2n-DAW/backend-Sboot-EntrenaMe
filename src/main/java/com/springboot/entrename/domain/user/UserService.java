package com.springboot.entrename.domain.user;

import com.springboot.entrename.api.user.UserDto;

public interface UserService {
    UserEntity getCurrentUser();

    UserEntity getUserByUsername(final String username);

    UserEntity getAdminUser();

    UserEntity updateCurrentUser(final UserDto.Update update);
}
