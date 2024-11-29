package com.springboot.entrename.api.user;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.ClientEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// import java.util.List;
// import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserAssembler {
    public UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImgUser())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .type_user(userEntity.getTypeUser())
            .client(userEntity.getIdClient() != null ? toClientResponse(userEntity.getIdClient()) : null) // Mapear datos del cliente 
            .build();
    }

    private ClientDto toClientResponse(ClientEntity clientEntity) {
        return ClientDto.builder()
            .id_client(clientEntity.getIdClient())
            .nif(clientEntity.getNif())
            .tlf(clientEntity.getTlf())
            .build();
    }

    
}
