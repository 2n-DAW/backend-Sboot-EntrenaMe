package com.springboot.entrename.domain.user;

import com.springboot.entrename.api.user.UserDto;

public interface UserService {
    // final indica que no se puede modificar dentro del cuerpo del método
    UserEntity getCurrentUser();

    UserEntity updateCurrentUser(final UserDto.Update update);
}
