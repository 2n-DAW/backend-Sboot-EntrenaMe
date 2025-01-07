package com.springboot.entrename.domain.user.client;

import com.springboot.entrename.api.user.client.ClientDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void updateClient(ClientEntity client, ClientDto.Update update) {
        if (update.getNif() != null) client.setNif(update.getNif());
        if (update.getTlf() != null) client.setTlf(update.getTlf());

        clientRepository.save(client);
    }
}
