package com.springboot.entrename.api.profile;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.api.user.UserAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {
    private final UserAssembler userAssembler;

    public ProfileDto.ProfileWrapper toPublicProfilesList(List<UserEntity> userEntities) {
        var content = userEntities.stream()
            .map(this::toPublicProfileResponse)
            .collect(Collectors.toList());

        return buildResponse(content, userEntities.size());
    }

    public ProfileDto toProfileResponse(UserEntity userEntity, Boolean isFollowed) {
        return buildProfile(userEntity, isFollowed, true);
    }

    public ProfileDto toPublicProfileResponse(UserEntity userEntity, boolean isFollowed) {
        return buildProfile(userEntity, isFollowed, false);
    }

    public ProfileDto toPublicProfileResponse(UserEntity userEntity) {
        return buildProfile(userEntity, null, false);
    }

    private ProfileDto buildProfile(UserEntity userEntity, Boolean isFollowed, boolean isOwner) {
        ProfileDto.ProfileDtoBuilder builder = ProfileDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .bio(userEntity.getBio())
            .is_followed(isFollowed);
        
        if (isOwner) {
            builder
                .email(userEntity.getEmail())
                .birthday(userEntity.getBirthday())
                .admin(userEntity.getId_admin() != null ? userAssembler.toAdminResponse(userEntity.getId_admin()) : null) // Mapear datos del admin
                .client(userEntity.getId_client() != null ? userAssembler.toClientResponse(userEntity.getId_client()) : null) // Mapear datos del cliente
                .instructor(userEntity.getId_instructor() != null ? userAssembler.toInstructorResponse(userEntity.getId_instructor()) : null); // Mapear datos del instructor
        }
        
        return builder.build();
    }

    private ProfileDto.ProfileWrapper buildResponse(List<ProfileDto> profiles, Number totalProfiles) {
        return ProfileDto.ProfileWrapper.builder()
            .profiles(profiles)
            .profiles_count(totalProfiles) 
            .build();
    }
}
