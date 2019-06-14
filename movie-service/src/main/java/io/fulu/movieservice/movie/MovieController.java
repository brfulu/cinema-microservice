package io.fulu.movieservice.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Movie> getMovies(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 @RequestParam("sortBy") Optional<String> sortBy,
                                 @RequestParam("search") Optional<String> search) {

        String sortField = sortBy.orElse("+name").substring(1);
        Sort sort = sortBy.orElse("+name").charAt(0) == '+' ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(1000), sort);

        return movieService.getMovies(pageable).stream().collect(Collectors.toList());
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
