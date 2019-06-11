package io.fulu.movieservice.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public void addMovie(@RequestBody Movie movie) {
        System.out.println("Add movie");
        movieService.saveMovie(movie);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Movie getMovie(@PathVariable long id) {
        return movieService.getMovieById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateMovie(@PathVariable long id, @RequestBody Movie movie) {
        movie.setId(id);
        movieService.saveMovie(movie);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteMovie(@PathVariable long id) {
        return movieService.deleteMovie(id);
    }
}
