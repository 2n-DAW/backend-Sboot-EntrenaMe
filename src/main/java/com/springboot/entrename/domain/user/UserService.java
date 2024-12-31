package com.springboot.entrename.domain.user;

import com.springboot.entrename.api.user.UserDto;

public interface UserService {
    UserEntity getCurrentUser();

    UserEntity updateCurrentUser(final UserDto.Update update);

    UserEntity getAdminUser();
}
