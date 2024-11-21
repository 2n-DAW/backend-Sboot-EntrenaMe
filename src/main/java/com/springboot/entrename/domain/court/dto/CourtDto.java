package com.springboot.entrename.domain.court.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourtDto {
    @NotNull
    private String nameCourt;

    private String imgCourt;
    private String slugCourt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SingleCourt<T> {
        private T court;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MultipleCourt {
        private List<CourtDto> courts;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update {
        private String nameCourt;
        private String imgCourt;
        private String slugCourt;
    }
}
