package com.springboot.entrename.api.notification;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.inscription.InscriptionEntity;
import com.springboot.entrename.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAssembler {
    public NotificationDto buildNotification(
        String to,
        String subject,
        String type_user,
        UserEntity user,
        ActivityEntity activity,
        InscriptionEntity inscription,
        String error
    ) {
        NotificationDto.DataInscription dataInscription = NotificationDto.DataInscription.builder()
            .name_client(user.getName())
            .surname_client(user.getSurname())
            .date(inscription.getDate().toString())
            .n_activity(activity.getNameActivity())
            .slot_hour(activity.getSlotHour())
            .slug_inscription(inscription.getSlugInscription())
            .name_instructor(activity.getIdUserInstructor().getName())
            .surname_instructor(activity.getIdUserInstructor().getSurname())
            .error(error)
            .build();
        
        return NotificationDto.builder()
            .to(to)
            .subject(subject)
            .type_user(type_user)
            .data_inscription(dataInscription)
            .build();
    }
}
