package com.springboot.entrename.api.user.client;

import com.springboot.entrename.api.user.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientAssembler {
    public ClientDto.Update toClientUpdate(UserDto.Update userUpdate) {
        return ClientDto.Update.builder()
            .nif(userUpdate.getNif())
            .tlf(userUpdate.getTlf())
            .build();
    }
}
