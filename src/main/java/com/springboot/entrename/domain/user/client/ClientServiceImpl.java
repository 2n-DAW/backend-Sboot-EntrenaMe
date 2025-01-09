package com.springboot.entrename.domain.user.client;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void saveClient(UserEntity savedUser, UserDto.Register register) {
        ClientEntity clientEntity = ClientEntity.builder()
            .id_user(savedUser)
            .nif(register.getClient().getNif())
            .tlf(register.getClient().getTlf())
            .build();

        clientRepository.save(clientEntity);
        savedUser.setId_client(clientEntity); // Asignar el client al user
    }

    @Transactional
    @Override
    public void updateClient(ClientEntity client, ClientDto.Update update) {
        if (update.getNif() != null) client.setNif(update.getNif());
        if (update.getTlf() != null) client.setTlf(update.getTlf());

        clientRepository.save(client);
    }
}
