package com.springboot.entrename.domain.user.client;

import com.springboot.entrename.api.user.client.ClientDto;

public interface ClientService {
    void updateClient(final ClientEntity client, final ClientDto.Update update);
}
