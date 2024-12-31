package com.springboot.entrename.api.inscription;

import com.springboot.entrename.domain.inscription.InscriptionEntity;
import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.api.activity.ActivityAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InscriptionAssembler {
    private final UserAssembler userAssembler;
    private final ActivityAssembler activityAssembler;

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
        return buildInscription(inscriptionEntity, false);
    }

    public InscriptionDto toInscriptiongWithUserAndActivityResponse(InscriptionEntity inscriptionEntity) {
        return buildInscription(inscriptionEntity, true);
    }

    private InscriptionDto buildInscription(InscriptionEntity inscriptionEntity, boolean detailed) {
        InscriptionDto.InscriptionDtoBuilder builder = InscriptionDto.builder()
            .id_inscription(inscriptionEntity.getId_inscription())
            .id_user_client(inscriptionEntity.getIdUserClient().getIdUser())
            .id_activity(inscriptionEntity.getIdActivity().getIdActivity())
            .date(inscriptionEntity.getDate())
            .state(inscriptionEntity.getState())
            .slug_inscription(inscriptionEntity.getSlugInscription());

        if (detailed) {
            builder
                .user(userAssembler.toUserWithoutPassResponse(inscriptionEntity.getIdUserClient()))
                .activity(activityAssembler.toActivityWithInstructorAndSportResponse(inscriptionEntity.getIdActivity()));
        }

        return builder.build();
    }

    private InscriptionDto.InscriptionWrapper buildResponse(List<InscriptionDto> inscriptions, Number totalInscriptions) {
        return InscriptionDto.InscriptionWrapper.builder()
                .inscriptions(inscriptions)
                .inscriptions_count(totalInscriptions) 
                .build();
    }
}
