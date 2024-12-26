package com.springboot.entrename.domain.booking;

import com.springboot.entrename.api.booking.BookingDto;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.domain.courtHour.CourtHourRepository;
import com.springboot.entrename.domain.courtHour.CourtHourService;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CourtHourService courtHourService;
    private final CourtHourRepository courtHourRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingEntity> getAllBookingsFromUser() {
        // Obtiene el usuario actual
        UserEntity user = userService.getCurrentUser();

        return bookingRepository.findByIdUserAndIsDeleted(user, 0);
    }

    @Transactional(readOnly = true)
    @Override
    public BookingEntity getBooking(String slug) {
        return bookingRepository.findBySlugBooking(slug)
            .orElseThrow(() -> new AppException(Error.BOOKING_NOT_FOUND));
    }

    @Transactional
    @Override
    public BookingEntity createBooking(BookingDto inscription) {

        // Obtiene el usuario actual
        UserEntity user = userService.getCurrentUser();

        // Comprueba que el horario de pista esté disponible
        CourtHourEntity courtHour = courtHourService.getCourtHourWithAvailability(inscription.getId_count_hours(), 1);

        // Cambia el estado del horario de la pista a no disponible
        courtHour.setAvailable(0);
        courtHourRepository.save(courtHour);

        // Crea la reserva de pista
        return create(user, courtHour);
    }

    @Transactional
    @Override
    public BookingEntity updateBooking(String slug, BookingDto inscription) {

        // Obtiene el usuario actual
        UserEntity user = userService.getCurrentUser();

        // Obtiene la reserva de pista actual
        BookingEntity currentBooking = getBooking(slug);
        
        // Obtiene el horario de pista actual
        CourtHourEntity currentCourtHour = courtHourService.getCourtHourWithAvailability(currentBooking.getId_count_hours().getIdCourtHour(), 0);


        // Cambia el estado del horario de la pista a disponible
        currentCourtHour.setAvailable(1);
        courtHourRepository.save(currentCourtHour);

        // Comprueba que el nuevo horario de pista esté disponible
        CourtHourEntity newCourtHour = courtHourService.getCourtHourWithAvailability(inscription.getId_count_hours(), 1);

        // Cambia el estado del horario de la pista a no disponible
        newCourtHour.setAvailable(0);
        courtHourRepository.save(newCourtHour);

        // Marca como eliminada la reserva de pista actual
        currentBooking.setIsDeleted(1);
        bookingRepository.save(currentBooking);

        // Crea la nueva reserva de pista
        return create(user, newCourtHour);
    }

    @Transactional
    @Override
    public BookingEntity deleteBooking(String slug) {

        // Obtiene la reserva de pista
        BookingEntity booking = getBooking(slug);

        // Obtiene el horario de pista reservado
        CourtHourEntity courtHour = courtHourService.getCourtHourWithAvailability(booking.getId_count_hours().getIdCourtHour(), 0);

        // Cambia el estado del horario de la pista a disponible
        courtHour.setAvailable(1);
        courtHourRepository.save(courtHour);

        // Marca como eliminada la reserva de pista actual
        booking.setIsDeleted(1);
        return bookingRepository.save(booking);
    }

    private BookingEntity create(UserEntity user, CourtHourEntity courtHour) {
        BookingEntity booking = BookingEntity.builder()
            .idUser(user)
            .id_count_hours(courtHour)
            .date(Date.valueOf(LocalDate.now()))
            .isDeleted(0)
            .slugBooking("booking-" + Instant.now())
            .build();

        return bookingRepository.save(booking);
    }
}
