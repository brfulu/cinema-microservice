package io.fulu.bookingservice.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Booking> getBookings() {
        return bookingService.getBookings();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addBooking(@RequestBody Booking booking) {
        bookingService.addBooking(booking);
    }
}
