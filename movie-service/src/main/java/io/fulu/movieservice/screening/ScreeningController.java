package io.fulu.movieservice.screening;

import io.fulu.movieservice.movie.MovieService;
import io.fulu.movieservice.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<ScreeningDto> getScreenings() {
        return screeningService.getScreenings().stream()
                .map(entity -> entityToDto(entity))
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
        screeningDto.setMovieId(screening.getMovie().getId());
        screeningDto.setRoomId(screening.getRoom().getId());
        return screeningDto;
    }

    private Screening dtoToEntity(ScreeningDto screeningDto) {
        Screening screening = new Screening();
        screening.setStart(screeningDto.getStart());
        screening.setStatus(screeningDto.getStatus());
        screening.setBookingCount(screeningDto.getBookingCount());
        screening.setMovie(movieService.getMovieById(screeningDto.getMovieId()));
        screening.setRoom(roomService.getRoomById(screeningDto.getRoomId()));
        return screening;
    }
}
