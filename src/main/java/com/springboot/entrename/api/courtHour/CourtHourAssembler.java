package com.springboot.entrename.api.courtHour;

import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.api.court.CourtAssembler;
import com.springboot.entrename.api.hour.HourAssembler;
import com.springboot.entrename.api.month.MonthAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtHourAssembler {
    private final CourtAssembler courtAssembler;
    private final HourAssembler hourAssembler;
    private final MonthAssembler monthAssembler;

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
                .hour(hourAssembler.toHourResponse(courtHourEntity.getId_hour()))
                .month(monthAssembler.toMonthResponse(courtHourEntity.getId_month()));
        }

        return builder.build();
    }

    private CourtHourDto.CourtHourWrapper buildResponse(List<CourtHourDto> courtsHours, Number totalCourtsHours) {
        return CourtHourDto.CourtHourWrapper.builder()
                .courts_hours(courtsHours)
                .courts_hours_count(totalCourtsHours) 
                .build();
    }
}
