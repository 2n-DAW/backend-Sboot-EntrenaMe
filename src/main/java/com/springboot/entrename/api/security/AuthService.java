package com.springboot.entrename.api.security;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.api.user.UserDto;

public interface AuthService {
    // final indica que no se puede modificar dentro del cuerpo del método
    UserEntity register(final UserDto.Register register);

    UserEntity login(final UserDto.Login login);
}
