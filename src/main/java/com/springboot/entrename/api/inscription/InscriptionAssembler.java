package com.springboot.entrename.api.inscription;

import com.springboot.entrename.domain.inscription.InscriptionEntity;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.activity.ActivityDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InscriptionAssembler {
    public InscriptionDto.InscriptionWrapper toInscriptionsList(List<InscriptionEntity> inscriptionEntities) {
        var content = inscriptionEntities.stream()
            .map(this::toInscriptionResponse)
            .collect(Collectors.toList());

        return buildResponse(content, inscriptionEntities.size());
    }

    public InscriptionDto.InscriptionWrapper toInscriptionsListWithUserAndActivity(List<InscriptionEntity> inscriptionEntities) {
        var content = inscriptionEntities.stream()
            .map(this::toInscriptiongWithUserAndActivityResponse)
            .collect(Collectors.toList());

        return buildResponse(content, inscriptionEntities.size());
    }

    public InscriptionDto.InscriptionWrapper toInscriptionsListFiltered(Page<InscriptionEntity> pageInscriptions) {
        var content = pageInscriptions.stream()
            .map(this::toInscriptionResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageInscriptions.getTotalElements());
    }

    public InscriptionDto.InscriptionWrapper toBookingsListWithUserAndCourtHourFiltered(Page<InscriptionEntity> pageInscriptions) {
        var content = pageInscriptions.stream()
            .map(this::toInscriptiongWithUserAndActivityResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageInscriptions.getTotalElements());
    }

    public InscriptionDto toInscriptionResponse(InscriptionEntity inscriptionEntity) {
        return InscriptionDto.builder()
            .id_inscription(inscriptionEntity.getId_inscription())
            .id_user_client(inscriptionEntity.getIdUserClient().getIdUser())
            .id_activity(inscriptionEntity.getIdActivity().getIdActivity())
            .date(inscriptionEntity.getDate())
            .state(inscriptionEntity.getState())
            .slug_inscription(inscriptionEntity.getSlugInscription())
            .build();
    }

    public InscriptionDto toInscriptiongWithUserAndActivityResponse(InscriptionEntity inscriptionEntity) {
        return InscriptionDto.builder()
            .id_inscription(inscriptionEntity.getId_inscription())
            .id_user_client(inscriptionEntity.getIdUserClient().getIdUser())
            .id_activity(inscriptionEntity.getIdActivity().getIdActivity())
            .date(inscriptionEntity.getDate())
            .state(inscriptionEntity.getState())
            .slug_inscription(inscriptionEntity.getSlugInscription())
            .user(toUserResponse(inscriptionEntity.getIdUserClient()))
            .activity(toActivityResponse(inscriptionEntity.getIdActivity()))
            .build();
    }

    private UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .type_user(userEntity.getTypeUser())
            .is_active(userEntity.getIs_active())
            .is_deleted(userEntity.getIs_deleted())
            .build();
    }

    //! Faltaria añadir info especifica para id_user_instructor, id_sport
    private ActivityDto toActivityResponse(ActivityEntity activityEntity) {
        return ActivityDto.builder()
            .id_activity(activityEntity.getIdActivity())
            .id_user_instructor(activityEntity.getIdUserInstructor().getIdUser())
            .id_sport(activityEntity.getIdSport().getIdSport())
            .n_activity(activityEntity.getNameActivity())
            .description(activityEntity.getDescription())
            .week_day(activityEntity.getWeekDay())
            .slot_hour(activityEntity.getSlotHour())
            .img_activity(activityEntity.getImgActivity())
            .spots(activityEntity.getSpots())
            .spots_available(activityEntity.getSpots_available())
            .slug_activity(activityEntity.getSlugActivity())
            .build();
    }

    private InscriptionDto.InscriptionWrapper buildResponse(List<InscriptionDto> inscriptions, Number totalInscriptions) {
        return InscriptionDto.InscriptionWrapper.builder()
                .inscriptions(inscriptions)
                .inscriptions_count(totalInscriptions) 
                .build();
    }
}
