package com.springboot.entrename.api.sport;

import com.springboot.entrename.api.activity.ActivityDto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.entrename.api.court.CourtDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SportDto {
    private Long id_sport;
    @NotNull
    private String n_sport;
    private String img_sport;
    private String slug_sport;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CourtDto> courts;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ActivityDto> activities;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportWrapper {
        private List<SportDto> sports;
        private Number sports_count;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String n_sport;
        @Nullable
        private String img_sport;
        @Nullable
        private String slug_sport;
    }
}
