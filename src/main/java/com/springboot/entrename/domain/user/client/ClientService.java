package com.springboot.entrename.domain.user.client;

import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.domain.user.UserEntity;

public interface ClientService {
    void saveClient(final UserEntity savedUser, final UserDto.Register register);

    void updateClient(final ClientEntity client, final ClientDto.Update update);
}
