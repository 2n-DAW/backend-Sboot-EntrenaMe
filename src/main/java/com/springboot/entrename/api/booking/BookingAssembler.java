package com.springboot.entrename.api.booking;

import com.springboot.entrename.domain.booking.BookingEntity;
import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.api.courtHour.CourtHourDto;
import com.springboot.entrename.api.user.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingAssembler {
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
        return BookingDto.builder()
            .id_booking(bookingEntity.getId_booking())
            .id_user(bookingEntity.getIdUser().getId_user())
            .id_count_hours(bookingEntity.getId_count_hours().getIdCourtHour())
            .date(bookingEntity.getDate())
            .is_deleted(bookingEntity.getIsDeleted())
            .slug_booking(bookingEntity.getSlugBooking())
            .build();
    }

    public BookingDto toBookingWithUserAndCourtHourResponse(BookingEntity bookingEntity) {
        return BookingDto.builder()
            .id_booking(bookingEntity.getId_booking())
            .id_user(bookingEntity.getIdUser().getId_user())
            .id_count_hours(bookingEntity.getId_count_hours().getIdCourtHour())
            .date(bookingEntity.getDate())
            .is_deleted(bookingEntity.getIsDeleted())
            .slug_booking(bookingEntity.getSlugBooking())
            .user(toUserResponse(bookingEntity.getIdUser()))
            .court_hour(toCourtHourResponse(bookingEntity.getId_count_hours()))
            .build();
    }

    private UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getId_user())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .name(userEntity.getName())
            .surname(userEntity.getSurname())
            .age(userEntity.getAge())
            .bio(userEntity.getBio())
            .type_user(userEntity.getType_user())
            .is_active(userEntity.getIs_active())
            .is_deleted(userEntity.getIs_deleted())
            .build();
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
