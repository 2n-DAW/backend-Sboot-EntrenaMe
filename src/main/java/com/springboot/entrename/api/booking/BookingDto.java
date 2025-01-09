package com.springboot.entrename.api.booking;

import com.springboot.entrename.api.courtHour.CourtHourDto;
import com.springboot.entrename.api.user.UserDto;

import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookingDto {
    private Long id_booking;
    @NotNull
    private UUID id_user;
    @NotNull
    private Long id_count_hours;
    @NotNull
    private Date date;
    private Integer is_deleted;
    private String slug_booking;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CourtHourDto.Response court_hour;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingWrapper {
        private List<BookingDto> bookings;
        private Number bookings_count;
    }
}
