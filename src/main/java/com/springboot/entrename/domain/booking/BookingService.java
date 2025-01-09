package com.springboot.entrename.domain.booking;

import com.springboot.entrename.api.booking.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingEntity> getAllBookings();

    List<BookingEntity> getAllBookingsFromUser();

    BookingEntity getBooking(final String slug);

    BookingEntity createBooking(final BookingDto inscription);

    BookingEntity updateBooking(final String slug, final BookingDto inscription);

    BookingEntity deleteBooking(final String slug);
}
