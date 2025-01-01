package com.springboot.entrename.api.booking;

import com.springboot.entrename.domain.booking.BookingService;

import com.springboot.entrename.api.security.authorization.CheckSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingAssembler bookingAssembler;

    @GetMapping
    @CheckSecurity.Protected.canManage
    public BookingDto.BookingWrapper getAllBookings() {
        var bookings = bookingService.getAllBookings();
        return bookingAssembler.toBookingsList(bookings);
    }

    @GetMapping("/detailed")
    @CheckSecurity.Protected.canManage
    public BookingDto.BookingWrapper getAllBookingsWithUserAndCourtHour() {
        var bookings = bookingService.getAllBookings();
        return bookingAssembler.toBookingsListWithUserAndCourtHour(bookings);
    }

    @GetMapping("/{slug}")
    @CheckSecurity.Protected.canManage
    public BookingDto getBooking(@PathVariable String slug) {
        var booking = bookingService.getBooking(slug);
        return bookingAssembler.toBookingResponse(booking);
    }

    @GetMapping("/detailed/{slug}")
    @CheckSecurity.Protected.canManage
    public BookingDto getBookingWithUserAndCourtHour(@PathVariable String slug) {
        var booking = bookingService.getBooking(slug);
        return bookingAssembler.toBookingWithUserAndCourtHourResponse(booking);
    }

    @GetMapping("/user")
    @CheckSecurity.Protected.canManage
    public BookingDto.BookingWrapper getAllBookingsFromUser() {
        var booking = bookingService.getAllBookingsFromUser();
        return bookingAssembler.toBookingsListWithUserAndCourtHour(booking);
    }

    @PostMapping("/create")
    @CheckSecurity.Protected.canManage
    public BookingDto createBooking(@RequestBody BookingDto inscription) {
        var booking = bookingService.createBooking(inscription);
        return bookingAssembler.toBookingWithUserAndCourtHourResponse(booking);
    }

    @PostMapping("/update/{slug}")
    @CheckSecurity.Protected.canManage
    public BookingDto updateBooking(@PathVariable String slug, @RequestBody BookingDto inscription) {
        var booking = bookingService.updateBooking(slug, inscription);
        return bookingAssembler.toBookingWithUserAndCourtHourResponse(booking);
    }

    @PutMapping("/delete/{slug}")
    @CheckSecurity.Protected.canManage
    public BookingDto deleteBooking(@PathVariable String slug) {
        var booking = bookingService.deleteBooking(slug);
        return bookingAssembler.toBookingWithUserAndCourtHourResponse(booking);
    }
}
