package com.springboot.entrename.domain.notification;

import com.springboot.entrename.api.notification.NotificationDto;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.inscription.InscriptionEntity;

public interface NotificationService {
    void sendNotification(final NotificationDto notification);

    NotificationDto buildNotification(
        final String to,
        final String subject,
        final String type_user,
        final UserEntity user,
        final ActivityEntity activity,
        final InscriptionEntity inscription,
        final String error);
}
