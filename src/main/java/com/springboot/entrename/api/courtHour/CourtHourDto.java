package com.springboot.entrename.api.courtHour;

import com.springboot.entrename.api.court.CourtDto;
import com.springboot.entrename.api.hour.HourDto;
import com.springboot.entrename.api.month.MonthDto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourtHourDto {
    private Long id_court_hour;
    @NotNull
    private Long id_court;
    @NotNull
    private Long id_hour;
    @NotNull
    private Long id_month;
    @NotNull
    private int day_number;
    @NotNull
    private int year;
    private String slug_court_hour;
    private Integer available;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CourtDto court;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HourDto hour;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MonthDto month;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourtHourWrapper {
        private List<CourtHourDto> courts_hours;
        private Number courts_hours_count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long id_court_hour;
        @NotNull
        private String n_court;
        @NotNull
        private String slot_hour;
        @NotNull
        private String n_month;
        @NotNull
        private int day_number;
        @NotNull
        private int year;
        private String slug_court_hour;
        private Integer available;
    }
}
