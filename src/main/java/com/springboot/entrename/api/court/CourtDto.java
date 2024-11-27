package com.springboot.entrename.api.court;

import lombok.*;
import javax.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.entrename.api.sport.SportDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourtDto {
    private Long id_court;
    @NotNull
    private String n_court;
    private String img_court;
    private String slug_court;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SportDto> sports;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourtWrapper {
        private List<CourtDto> courts;
        private int courts_count;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String n_court;
        @Nullable
        private String img_court;
        @Nullable
        private String slug_court;
    }
}
