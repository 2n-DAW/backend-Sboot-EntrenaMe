package com.springboot.entrename.api.user;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.AdminEntity;
import com.springboot.entrename.domain.user.ClientEntity;
import com.springboot.entrename.domain.user.InstructorEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
            .admin(userEntity.getIdAdmin() != null ? toAdminResponse(userEntity.getIdAdmin()) : null) // Mapear datos del admin
            .client(userEntity.getIdClient() != null ? toClientResponse(userEntity.getIdClient()) : null) // Mapear datos del cliente
            .instructor(userEntity.getIdInstructor() != null ? toInstructorResponse(userEntity.getIdInstructor()) : null) // Mapear datos del instructor
            .build();
    }

    public UserDto.UserWithToken toLoginResponse(UserEntity userEntity, String token) {
        return UserDto.UserWithToken.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImgUser())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .type_user(userEntity.getTypeUser())
            .token(token != null ? token : null)
            .admin(userEntity.getIdAdmin() != null ? toAdminResponse(userEntity.getIdAdmin()) : null) // Mapear datos del admin
            .client(userEntity.getIdClient() != null ? toClientResponse(userEntity.getIdClient()) : null) // Mapear datos del cliente
            .instructor(userEntity.getIdInstructor() != null ? toInstructorResponse(userEntity.getIdInstructor()) : null) // Mapear datos del instructor
            .build();
    }

    // Método para mapear los datos del admin
    private AdminDto toAdminResponse(AdminEntity adminEntity) {
        return AdminDto.builder()
            .id_admin(adminEntity.getIdAdmin())
            .id_user(adminEntity.getIdUser().getIdUser())
            .build();
    }

    // Método para mapear los datos del cliente
    private ClientDto toClientResponse(ClientEntity clientEntity) {
        return ClientDto.builder()
            .id_client(clientEntity.getIdClient())
            .id_user(clientEntity.getIdUser().getIdUser())
            .nif(clientEntity.getNif())
            .tlf(clientEntity.getTlf())
            .build();
    }

    // Método para mapear los datos del instructor
    private InstructorDto toInstructorResponse(InstructorEntity instructorEntity) {
        return InstructorDto.builder()
            .id_instructor(instructorEntity.getIdInstructor())
            .id_user(instructorEntity.getIdUser().getIdUser())
            .nif(instructorEntity.getNif())
            .tlf(instructorEntity.getTlf())
            .address(instructorEntity.getAddress())
            .build();
    }
}
