package com.springboot.entrename.api.profile;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.api.user.UserAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {
    private final UserAssembler userAssembler;

    public ProfileDto toProfileResponse(UserEntity userEntity) {
        return buildProfile(userEntity, true);
    }

    public ProfileDto toPublicProfileResponse(UserEntity userEntity) {
        return buildProfile(userEntity, false);
    }

    private ProfileDto buildProfile(UserEntity userEntity, boolean authenticated) {
        ProfileDto.ProfileDtoBuilder builder = ProfileDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .bio(userEntity.getBio());
        
        if (authenticated) {
            builder
                .email(userEntity.getEmail())
                .age(userEntity.getAge())
                .admin(userEntity.getId_admin() != null ? userAssembler.toAdminResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
                .client(userEntity.getId_client() != null ? userAssembler.toClientResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
                .instructor(userEntity.getId_instructor() != null ? userAssembler.toInstructorResponse(userEntity.getId_instructor()) : null); // Mapear datos del instructor
        }
        
        return builder.build();
    }
}
