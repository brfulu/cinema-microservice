package io.fulu.bookingservice.bookings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @RequestMapping(method = RequestMethod.GET)
    public String getBookings() {
        return "Bookings";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addBooking() {
        return "Booked";
    }
}
