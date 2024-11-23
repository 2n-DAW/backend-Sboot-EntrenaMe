package com.springboot.entrename.api.sport.dto;

import com.springboot.entrename.api.court.dto.CourtDto;

import lombok.*;
import javax.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SportDto {
    private Long idSport;
    @NotNull
    private String nameSport;
    private String imgSport;
    private String slugSport;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CourtDto> courts;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportWrapper {
        private List<SportDto> sports;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportUpdate {
        @Nullable
        private String nameSport;
        @Nullable
        private String imgSport;
        @Nullable
        private String slugSport;
    }
}
