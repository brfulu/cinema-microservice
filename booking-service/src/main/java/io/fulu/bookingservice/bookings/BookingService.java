package io.fulu.bookingservice.bookings;

import io.fulu.bookingservice.models.AppUserDto;
import io.fulu.bookingservice.models.ScreeningDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public JavaMailSender emailSender;

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public boolean addBooking(Booking booking) {
        // dovuci podatke o projekciji
        ScreeningDto screeningDto = restTemplate.getForObject(
                "http://localhost:8082/screenings/" + booking.getScreeningId(),
                ScreeningDto.class);
        // proveri da li ima mesta
        if (screeningDto.getStatus().equals("AVAILABLE")) {
            // ako ima mesta dovuci podatke za korisnika
            AppUserDto user = restTemplate.getForObject("http://localhost:8083/users/" + booking.getUserId(),
                    AppUserDto.class);

            // izracunaj cenu sa popustom
            System.out.println(user.getStatus().getDiscount());
            double totalPrice = booking.getSeats() * screeningDto.getTicketPrice();
            booking.setTotalPrice(totalPrice - totalPrice * user.getStatus().getDiscount());

            // azuriraj projekciju
            screeningDto.setBookingCount(screeningDto.getBookingCount() + booking.getSeats());
            HttpEntity<ScreeningDto> screeningRequest = new HttpEntity<>(screeningDto);
            restTemplate.put("http://localhost:8082/screenings/" + screeningDto.getId(), screeningRequest);

            // azuriraj korisnika
            user.setBookingCount(user.getBookingCount() + booking.getSeats());
            HttpEntity<AppUserDto> userRequest = new HttpEntity<>(user);
            restTemplate.put("http://localhost:8083/users/" + user.getId(), userRequest);

            // snimi rezervaciju
            bookingRepository.save(booking);

            // posalji mejl korisniku
            sendSimpleMessage("brankofulurija@gmail.com", "Proba subject", "Ovo je tekst");
            return true;
        }
        return false;
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
