package com.springboot.entrename.domain.courtHour;

import java.util.List;

public interface CourtHourService {
    List<CourtHourEntity> getAllCourtsHours();

    CourtHourEntity getCourtHourWithAvailability(final Long id, final int available);

    List<CourtHourEntity> getAvailableCourtsHours(final Long id_court);
}
