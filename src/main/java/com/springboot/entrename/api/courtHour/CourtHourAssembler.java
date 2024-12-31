package com.springboot.entrename.api.courtHour;

import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.domain.hour.HourEntity;
import com.springboot.entrename.domain.month.MonthEntity;
import com.springboot.entrename.api.hour.HourDto;
import com.springboot.entrename.api.month.MonthDto;
import com.springboot.entrename.api.court.CourtAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtHourAssembler {
    private final CourtAssembler courtAssembler;

    public CourtHourDto.CourtHourWrapper toCourtsHoursList(List<CourtHourEntity> courtHourEntities) {
        var content = courtHourEntities.stream()
            .map(this::toCourtHourResponse)
            .collect(Collectors.toList());

        return buildResponse(content, courtHourEntities.size());
    }

    public CourtHourDto.CourtHourWrapper toDetailedCourtsHoursList(List<CourtHourEntity> courtHourEntities) {
        var content = courtHourEntities.stream()
            .map(this::toDetailedCourtHourResponse)
            .collect(Collectors.toList());

        return buildResponse(content, courtHourEntities.size());
    }

    public CourtHourDto toCourtHourResponse(CourtHourEntity courtHourEntity) {
        return buildCourtHour(courtHourEntity, false);
    }

    public CourtHourDto toDetailedCourtHourResponse(CourtHourEntity courtHourEntity) {
        return buildCourtHour(courtHourEntity, true);
    }

    private CourtHourDto buildCourtHour(CourtHourEntity courtHourEntity, boolean detailed) {
        CourtHourDto.CourtHourDtoBuilder builder = CourtHourDto.builder()
            .id_court_hour(courtHourEntity.getIdCourtHour())
            .id_court(courtHourEntity.getId_court().getIdCourt())
            .id_hour(courtHourEntity.getId_hour().getIdHour())
            .id_month(courtHourEntity.getId_month().getIdMonth())
            .day_number(courtHourEntity.getDay_number())
            .year(courtHourEntity.getYear())
            .slug_court_hour(courtHourEntity.getSlug_court_hour())
            .available(courtHourEntity.getAvailable());

        if (detailed) {
            builder
                .court(courtAssembler.toCourtResponse(courtHourEntity.getId_court()))
                .hour(toHourResponse(courtHourEntity.getId_hour()))
                .month(toMonthResponse(courtHourEntity.getId_month()));
        }

        return builder.build();
    }

    private HourDto toHourResponse(HourEntity hourEntity) {
        return HourDto.builder()
            .id_hour(hourEntity.getIdHour())
            .slot_hour(hourEntity.getSlotHour())
            .build();
    }

    private MonthDto toMonthResponse(MonthEntity monthEntity) {
        return MonthDto.builder()
            .id_month(monthEntity.getIdMonth())
            .n_month(monthEntity.getNameMonth())
            .slug_month(monthEntity.getSlugMonth())
            .build();
    }

    private CourtHourDto.CourtHourWrapper buildResponse(List<CourtHourDto> courtsHours, Number totalCourtsHours) {
        return CourtHourDto.CourtHourWrapper.builder()
                .courts_hours(courtsHours)
                .courts_hours_count(totalCourtsHours) 
                .build();
    }
}
