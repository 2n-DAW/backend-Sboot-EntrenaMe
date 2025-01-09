package com.springboot.entrename.api.booking;

import com.springboot.entrename.domain.booking.BookingEntity;
import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.api.courtHour.CourtHourDto;
import com.springboot.entrename.api.user.UserAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingAssembler {
    private final UserAssembler userAssembler;

    public BookingDto.BookingWrapper toBookingsList(List<BookingEntity> bookingEntities) {
        var content = bookingEntities.stream()
            .map(this::toBookingResponse)
            .collect(Collectors.toList());

        return buildResponse(content, bookingEntities.size());
    }

    public BookingDto.BookingWrapper toBookingsListWithUserAndCourtHour(List<BookingEntity> bookingEntities) {
        var content = bookingEntities.stream()
            .map(this::toBookingWithUserAndCourtHourResponse)
            .collect(Collectors.toList());

        return buildResponse(content, bookingEntities.size());
    }

    public BookingDto.BookingWrapper toBookingListFiltered(Page<BookingEntity> pageBookings) {
        var content = pageBookings.stream()
            .map(this::toBookingResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageBookings.getTotalElements());
    }

    public BookingDto.BookingWrapper toBookingsListWithUserAndCourtHourFiltered(Page<BookingEntity> pageBookings) {
        var content = pageBookings.stream()
            .map(this::toBookingWithUserAndCourtHourResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageBookings.getTotalElements());
    }

    public BookingDto toBookingResponse(BookingEntity bookingEntity) {
        return buildBooking(bookingEntity, false);
    }

    public BookingDto toBookingWithUserAndCourtHourResponse(BookingEntity bookingEntity) {
        return buildBooking(bookingEntity, true);
    }

    private BookingDto buildBooking(BookingEntity bookingEntity, boolean detailed) {
        BookingDto.BookingDtoBuilder builder = BookingDto.builder()
            .id_booking(bookingEntity.getId_booking())
            .id_user(bookingEntity.getIdUser().getIdUser())
            .id_count_hours(bookingEntity.getId_count_hours().getIdCourtHour())
            .date(bookingEntity.getDate())
            .is_deleted(bookingEntity.getIsDeleted())
            .slug_booking(bookingEntity.getSlugBooking());

        if (detailed) {
            builder
                .user(userAssembler.toUserWithoutPassResponse(bookingEntity.getIdUser()))
                .court_hour(toCourtHourResponse(bookingEntity.getId_count_hours()));
        }

        return builder.build();
    }

    private CourtHourDto.Response toCourtHourResponse(CourtHourEntity courtHourEntity) {
        return CourtHourDto.Response.builder()
            .id_court_hour(courtHourEntity.getIdCourtHour())
            .n_court(courtHourEntity.getId_court().getNameCourt())
            .slot_hour(courtHourEntity.getId_hour().getSlotHour())
            .n_month(courtHourEntity.getId_month().getNameMonth())
            .day_number(courtHourEntity.getDay_number())
            .year(courtHourEntity.getYear())
            .slug_court_hour(courtHourEntity.getSlug_court_hour())
            .available(courtHourEntity.getAvailable())
            .build();
    }

    private BookingDto.BookingWrapper buildResponse(List<BookingDto> bookings, Number totalBookings) {
        return BookingDto.BookingWrapper.builder()
                .bookings(bookings)
                .bookings_count(totalBookings) 
                .build();
    }
}
