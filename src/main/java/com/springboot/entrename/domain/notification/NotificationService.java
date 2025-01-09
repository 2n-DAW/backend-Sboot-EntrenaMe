package com.springboot.entrename.domain.notification;

import com.springboot.entrename.api.notification.NotificationDto;

public interface NotificationService {
    void sendNotification(final NotificationDto notification);
}
