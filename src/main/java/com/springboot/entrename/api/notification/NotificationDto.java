package com.springboot.entrename.api.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import io.micrometer.common.lang.Nullable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NotificationDto {
    @NotNull
    private String to;
    @NotNull
    private String subject;
    @NotNull
    private String type_user;
    @NotNull
    private DataInscription data_inscription;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataInscription {
        private String name_client;
        private String surname_client;
        private String date;
        private String n_activity;
        private String slot_hour;
        private String slug_inscription;
        private String name_instructor;
        private String surname_instructor;
        @Nullable
        private String error;
    }
}
