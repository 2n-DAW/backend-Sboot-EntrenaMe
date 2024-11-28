package com.springboot.entrename.api.activity;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.sport.SportDto;

import lombok.*;
import javax.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ActivityDto {
    private Long id_activity;
    @NotNull
    private Long id_user_instructor;
    @NotNull
    private Long id_sport;
    @NotNull
    private String n_activity;
    private String description;
    private String week_day;
    private String slot_hour;
    private String img_activity;
    private int spots;
    private String slug_activity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto instructor;
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    // private UserDto.Instructor instructor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SportDto sport;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourtWrapper {
        private List<ActivityDto> activities;
        private int activities_count;
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
    }
}
