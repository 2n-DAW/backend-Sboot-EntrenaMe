package com.springboot.entrename.api.profile;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.admin.AdminEntity;
import com.springboot.entrename.domain.user.client.ClientEntity;
import com.springboot.entrename.domain.user.instructor.InstructorEntity;
import com.springboot.entrename.api.user.admin.AdminDto;
import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.api.user.instructor.InstructorDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {
    public ProfileDto toProfileResponse(UserEntity userEntity) {
        return ProfileDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImgUser())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .admin(userEntity.getIdAdmin() != null ? toAdminProfileResponse(userEntity.getIdAdmin()) : null) // Mapear datos del admin
            .client(userEntity.getIdClient() != null ? toClientProfileResponse(userEntity.getIdClient()) : null) // Mapear datos del cliente
            .instructor(userEntity.getIdInstructor() != null ? toInstructorProfileResponse(userEntity.getIdInstructor()) : null) // Mapear datos del instructor
            .build();
    }

    public ProfileDto toPublicProfileResponse(UserEntity userEntity) {
        return ProfileDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImgUser())
            .username(userEntity.getUsername())
            .build();
    }

    // Método para mapear los datos del admin
    private AdminDto toAdminProfileResponse(AdminEntity adminEntity) {
        return AdminDto.builder()
            .id_admin(adminEntity.getIdAdmin())
            .id_user(adminEntity.getIdUser().getIdUser())
            .build();
    }

    // Método para mapear los datos del cliente
    private ClientDto toClientProfileResponse(ClientEntity clientEntity) {
        return ClientDto.builder()
            .id_client(clientEntity.getIdClient())
            .id_user(clientEntity.getIdUser().getIdUser())
            .nif(clientEntity.getNif())
            .tlf(clientEntity.getTlf())
            .build();
    }

    // Método para mapear los datos del instructor
    private InstructorDto toInstructorProfileResponse(InstructorEntity instructorEntity) {
        return InstructorDto.builder()
            .id_instructor(instructorEntity.getIdInstructor())
            .id_user(instructorEntity.getIdUser().getIdUser())
            .nif(instructorEntity.getNif())
            .tlf(instructorEntity.getTlf())
            .address(instructorEntity.getAddress())
            .build();
    }
}
