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
            .id_user(userEntity.getId_user())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .admin(userEntity.getId_admin() != null ? toAdminProfileResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
            .client(userEntity.getId_client() != null ? toClientProfileResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
            .instructor(userEntity.getId_instructor() != null ? toInstructorProfileResponse(userEntity.getId_instructor()) : null) // Mapear datos del instructor
            .build();
    }

    public ProfileDto toPublicProfileResponse(UserEntity userEntity) {
        return ProfileDto.builder()
            .id_user(userEntity.getId_user())
            .img_user(userEntity.getImg_user())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .bio(userEntity.getBio())
            .build();
    }

    // Método para mapear los datos del admin
    private AdminDto toAdminProfileResponse(AdminEntity adminEntity) {
        return AdminDto.builder()
            .id_admin(adminEntity.getId_admin())
            .id_user(adminEntity.getId_user().getId_user())
            .build();
    }

    // Método para mapear los datos del cliente
    private ClientDto toClientProfileResponse(ClientEntity clientEntity) {
        return ClientDto.builder()
            .id_client(clientEntity.getId_client())
            .id_user(clientEntity.getId_user().getId_user())
            .nif(clientEntity.getNif())
            .tlf(clientEntity.getTlf())
            .build();
    }

    // Método para mapear los datos del instructor
    private InstructorDto toInstructorProfileResponse(InstructorEntity instructorEntity) {
        return InstructorDto.builder()
            .id_instructor(instructorEntity.getId_instructor())
            .id_user(instructorEntity.getId_user().getId_user())
            .nif(instructorEntity.getNif())
            .tlf(instructorEntity.getTlf())
            .address(instructorEntity.getAddress())
            .build();
    }
}
