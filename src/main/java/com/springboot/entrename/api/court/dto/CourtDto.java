package com.springboot.entrename.api.court.dto;

import com.springboot.entrename.api.sport.dto.SportDto;

import lombok.*;
import javax.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourtDto {
    private Long idCourt;
    @NotNull
    private String nameCourt;
    private String imgCourt;
    private String slugCourt;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SportDto> sports;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourtWrapper {
        private List<CourtDto> courts;
        private int courtsCount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourtUpdate {
        @Nullable
        private String nameCourt;
        @Nullable
        private String imgCourt;
        @Nullable
        private String slugCourt;
    }
}
