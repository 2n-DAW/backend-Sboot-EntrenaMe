package com.springboot.entrename.api.activity;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.sport.SportDto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ActivityDto {
    private Long id_activity;
    @NotNull
    private UUID id_user_instructor;
    @NotNull
    private Long id_sport;
    @NotNull
    private String n_activity;
    private String description;
    private String week_day;
    private String slot_hour;
    private String img_activity;
    private int spots;
    private int spots_available;
    private String slug_activity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto instructor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SportDto sport;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityWrapper {
        private List<ActivityDto> activities;
        private Number activities_count;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String description;
        @Nullable
        private String week_day;
        @Nullable
        private String slot_hour;
        @Nullable
        private String img_activity;
        @Nullable
        private int spots;
        @Nullable
        private int spots_available;
    }
}
