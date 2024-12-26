package com.springboot.entrename.domain.courtHour;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
// import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtHourServiceImpl implements CourtHourService {
    private final CourtHourRepository courtHourRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CourtHourEntity> getAllCourtsHours() {
        return courtHourRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public CourtHourEntity getCourtHourWithAvailability(Long id, int available) {
        return courtHourRepository.findByIdCourtHourAndAvailable(id, available)
            .orElseThrow(() -> new AppException(Error.COURT_HOUR_NOT_AVAILABLE));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourtHourEntity> getAvailableCourtsHours(Long id_court) {
        int year = LocalDate.now().getYear();
        // System.err.println("year ============================================ " + year);
        int currentMonth = LocalDate.now().getMonth().getValue();
        int nextMonth = currentMonth == 12 ? 1 : currentMonth++;
        List<Long> months = List.of((long) currentMonth, (long) nextMonth);
        // System.err.println("currentMonth ============================================ " + currentMonth);
        // System.err.println("nextMonth ============================================ " + nextMonth);
        int day_number = LocalDate.now().getDayOfMonth();
        // System.err.println("currentDay ============================================ " + day_number);
        // int hour = LocalTime.now().getHour();
        // System.err.println("hour ============================================ " + hour);

        List<CourtHourEntity> courtHours = courtHourRepository.findById_courtAndYearAndId_monthInAndDay_numberGreaterThanEqual(
            id_court, year, months, day_number);
        
        if (courtHours.isEmpty()) {
            throw new AppException(Error.COURT_HOUR_NOT_FOUND);
        }

        return courtHours;
    }
}
