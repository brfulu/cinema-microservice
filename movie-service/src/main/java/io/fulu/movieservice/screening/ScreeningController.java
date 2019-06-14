package io.fulu.movieservice.screening;

import io.fulu.movieservice.movie.MovieService;
import io.fulu.movieservice.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RoomService roomService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public void addScreening(@RequestBody ScreeningDto screeningDto) {
        screeningService.saveScreening(dtoToEntity(screeningDto));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ScreeningDto> getScreenings(@RequestParam("page") Optional<Integer> page,
                                            @RequestParam("size") Optional<Integer> size,
                                            @RequestParam("sortBy") Optional<String> sortBy,
                                            @RequestParam("search") Optional<String> search) {

        String sortField = sortBy.orElse("+ticketPrice").substring(1);
        Sort sort = sortBy.orElse("+ticketPrice").charAt(0) == '+' ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(1000), sort);

        return screeningService.getScreenings(pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ScreeningDto getScreening(@PathVariable long id) {
        return entityToDto(screeningService.getScreeningById(id));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateScreening(@PathVariable long id, @RequestBody ScreeningDto screeningDto) {
        Screening screening = dtoToEntity(screeningDto);
        screening.setId(id);
        screeningService.saveScreening(screening);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteScreening(@PathVariable long id) {
        return screeningService.deleteScreening(id);
    }

    private ScreeningDto entityToDto(Screening screening) {
        ScreeningDto screeningDto = new ScreeningDto();
        screeningDto.setStart(screening.getStart());
        screeningDto.setStatus(screening.getStatus());
        screeningDto.setBookingCount(screening.getBookingCount());
        screeningDto.setMovie(screening.getMovie());
        screeningDto.setMovieId(screening.getMovie().getId());
        screeningDto.setRoom(screening.getRoom());
        screeningDto.setRoomId(screening.getRoom().getId());
        screeningDto.setTicketPrice(screening.getTicketPrice());
        screeningDto.setId(screening.getId());
        return screeningDto;
    }

    private Screening dtoToEntity(ScreeningDto screeningDto) {
        Screening screening = new Screening();
        screening.setStart(screeningDto.getStart());
        screening.setStatus(screeningDto.getStatus());
        screening.setBookingCount(screeningDto.getBookingCount());
        screening.setMovie(movieService.getMovieById(screeningDto.getMovieId()));
        screening.setRoom(roomService.getRoomById(screeningDto.getRoomId()));
        screening.setTicketPrice(screeningDto.getTicketPrice());
        return screening;
    }
}
