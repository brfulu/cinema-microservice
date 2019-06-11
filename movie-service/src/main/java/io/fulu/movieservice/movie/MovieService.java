package io.fulu.movieservice.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public boolean deleteMovie(long id) {
        try {
            movieRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
