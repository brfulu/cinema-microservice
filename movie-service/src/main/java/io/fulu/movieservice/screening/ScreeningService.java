package io.fulu.movieservice.screening;

import io.fulu.movieservice.movie.MovieRepository;
import io.fulu.movieservice.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningService {
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RoomRepository roomRepository;

    public void saveScreening(Screening screening) {
        screeningRepository.save(screening);
    }

    public Page<Screening> getScreenings(Pageable pageable) {
        return screeningRepository.findAll(pageable);
    }

    public Screening getScreeningById(long id) {
        return screeningRepository.findById(id).orElse(new Screening());
    }

    public boolean deleteScreening(long id) {
        try {
            screeningRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
