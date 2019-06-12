package io.fulu.bookingservice.bookings;

import io.fulu.bookingservice.models.ApplicationUser;
import io.fulu.bookingservice.models.ScreeningDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public void addBooking(Booking booking) {
        // dovuci podatke o projekciji
        ScreeningDto screeningDto = restTemplate.getForObject(
                "http://localhost:8082/screenings/" + booking.getScreeningId(),
                ScreeningDto.class);
        // proveri da li ima mesta
        if (screeningDto.getStatus().equals("AVAILABLE")) {
            // ako ima mesta dovuci podatke za korisnika
            ApplicationUser user = restTemplate.getForObject("http://localhost:8083/users/" + booking.getUserId(),
                    ApplicationUser.class);

            // izracunaj cenu sa popustom
            System.out.println(user.getStatus().getDiscount());
            double totalPrice = booking.getSeats().size() * screeningDto.getTicketPrice();
            booking.setTotalPrice(totalPrice - totalPrice * user.getStatus().getDiscount());

            // azuriraj projekciju
            screeningDto.setBookingCount(screeningDto.getBookingCount() + booking.getSeats().size());
            HttpEntity<ScreeningDto> screeningRequest = new HttpEntity<>(screeningDto);
            restTemplate.put("http://localhost:8082/screenings/" + screeningDto.getId(), screeningRequest);

            // azuriraj korisnika
            user.setBookingCount(user.getBookingCount() + booking.getSeats().size());
            HttpEntity<ApplicationUser> userRequest = new HttpEntity<>(user);
            restTemplate.put("http://localhost:8083/users/" + user.getId(), userRequest);

            // snimi rezervaciju
            bookingRepository.save(booking);

            // posalji mejl korisniku
        }
    }
}
