package com.springboot.entrename.domain.courtHour;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
        // System.out.println("Year ====================================================== " + year);
        int currentMonth = LocalDate.now().getMonth().getValue()-1;
        int nextMonth = currentMonth == 11 ? 1 : LocalDate.now().getMonth().getValue();
        List<Long> months = List.of((long) currentMonth, (long) nextMonth);
        // System.out.println("Current month ====================================================== " + currentMonth);
        // System.out.println("Next month ====================================================== " + nextMonth);
        int day_number = LocalDate.now().getDayOfMonth();
        // System.out.println("Day of month ====================================================== " + day_number);
        int id_hour = LocalTime.now().getHour();
        // System.out.println("Hour ====================================================== " + id_hour);
        
        List<CourtHourEntity> courtHours = courtHourRepository.findHoursForCurrentAndNextMonth(
            id_court, year, months, day_number, id_hour);
        
        if (courtHours.isEmpty()) {
            throw new AppException(Error.COURT_HOUR_NOT_FOUND);
        }

        return courtHours;
    }
}
