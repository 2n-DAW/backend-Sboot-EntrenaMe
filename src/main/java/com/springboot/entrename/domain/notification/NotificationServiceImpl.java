package com.springboot.entrename.domain.notification;

import com.springboot.entrename.api.notification.NotificationDto;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.inscription.InscriptionEntity;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.springboot.entrename.domain.exception.NotificationException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

// Para debugear en consola
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final WebClient mailgunWebClient;
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class); // Para debugear en consola

    @Override
    public void sendNotification(NotificationDto notification) throws NotificationException {
        try {
            mailgunWebClient.post()
                .uri("/notifications/email")
                .bodyValue(notification)
                .retrieve()
                .toEntity(String.class)
                .block();
                    
        } catch (WebClientResponseException e) {
            logger.error("Error en WebClient: {}", e.getMessage());
            throw new NotificationException(
                "Error al comunicarse con el servidor de Mailgun",
                e.getStatusCode().value(),
                e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Error inesperado al enviar notificación: {}", e.getMessage());
            throw new NotificationException(
                "Error inesperado al enviar notificación",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
            );
        }
    }

    @Override
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
