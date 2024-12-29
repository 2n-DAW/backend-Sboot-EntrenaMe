package com.springboot.entrename.api.user;

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
public class UserAssembler {
    public UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .password(userEntity.getPassword())
            .type_user(userEntity.getType_user())
            .is_active(userEntity.getIs_active())
            .is_deleted(userEntity.getIs_deleted())
            .admin(userEntity.getId_admin() != null ? toAdminResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
            .client(userEntity.getId_client() != null ? toClientResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
            .instructor(userEntity.getId_instructor() != null ? toInstructorResponse(userEntity.getId_instructor()) : null) // Mapear datos del instructor
            .build();
    }

    public UserDto.UserWithToken toLoginResponse(UserEntity userEntity, String token) {
        return UserDto.UserWithToken.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .password(userEntity.getPassword())
            .type_user(userEntity.getType_user())
            .is_active(userEntity.getIs_active())
            .is_deleted(userEntity.getIs_deleted())
            .token(token != null ? token : null)
            .admin(userEntity.getId_admin() != null ? toAdminResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
            .client(userEntity.getId_client() != null ? toClientResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
            .instructor(userEntity.getId_instructor() != null ? toInstructorResponse(userEntity.getId_instructor()) : null) // Mapear datos del instructor
            .build();
    }

    public UserDto toCurrentUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .type_user(userEntity.getType_user())
            .is_active(userEntity.getIs_active())
            .is_deleted(userEntity.getIs_deleted())
            .admin(userEntity.getId_admin() != null ? toAdminResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
            .client(userEntity.getId_client() != null ? toClientResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
            .instructor(userEntity.getId_instructor() != null ? toInstructorResponse(userEntity.getId_instructor()) : null) // Mapear datos del instructor
            .build();
    }

    // Método para mapear los datos del admin
    private AdminDto toAdminResponse(AdminEntity adminEntity) {
        return AdminDto.builder()
            .id_admin(adminEntity.getId_admin())
            .id_user(adminEntity.getId_user().getIdUser())
            .build();
    }

    // Método para mapear los datos del cliente
    private ClientDto toClientResponse(ClientEntity clientEntity) {
        return ClientDto.builder()
            .id_client(clientEntity.getId_client())
            .id_user(clientEntity.getId_user().getIdUser())
            .nif(clientEntity.getNif())
            .tlf(clientEntity.getTlf())
            .build();
    }

    // Método para mapear los datos del instructor
    private InstructorDto toInstructorResponse(InstructorEntity instructorEntity) {
        return InstructorDto.builder()
            .id_instructor(instructorEntity.getId_instructor())
            .id_user(instructorEntity.getId_user().getIdUser())
            .nif(instructorEntity.getNif())
            .tlf(instructorEntity.getTlf())
            .address(instructorEntity.getAddress())
            .build();
    }
}
