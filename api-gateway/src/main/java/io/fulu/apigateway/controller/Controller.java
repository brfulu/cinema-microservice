package io.fulu.apigateway.controller;

import io.fulu.apigateway.model.AppUserDto;
import io.fulu.apigateway.model.Movie;
import io.fulu.apigateway.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class Controller {
    private final String userServiceUrl = "http://localhost:8083";
    private final String movieServiceUrl = "http://localhost:8082";
    private final String bookingServiceUrl = "http://localhost:8081";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody SimpleUser user) {
        ResponseEntity response = restTemplate.postForEntity(userServiceUrl + "/login", user, SimpleUser.class);
        return response;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody AppUserDto appUserDto) {
        restTemplate.postForLocation(userServiceUrl + "/users/sign-up", appUserDto);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestParam("username") Optional<String> username, HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(req.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity response = restTemplate.exchange(userServiceUrl + "/users?username=" + username.orElse(""),
                HttpMethod.GET, entity, ArrayList.class);
        return response;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody AppUserDto userDto, HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(req.getHeader("Authorization"));
        HttpEntity<AppUserDto> entity = new HttpEntity<>(userDto, headers);

        ResponseEntity response = restTemplate.exchange(userServiceUrl + "/users/" + id,
                HttpMethod.PUT, entity, AppUserDto.class);
        return response;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public ResponseEntity getMovies(HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(req.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity response = restTemplate.exchange(movieServiceUrl + "/movies",
                HttpMethod.GET, entity, ArrayList.class);
        return response;
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.DELETE)
    public void deleteMovie(@PathVariable long id) {
        restTemplate.delete(movieServiceUrl + "/movies/" + id);
    }

    @RequestMapping(path = "/movies/{id}", method = RequestMethod.PUT)
    public void updateMovie(@PathVariable long id, @RequestBody Movie movie) {
        restTemplate.put(movieServiceUrl + "/movies/" + id, movie);
    }

    @RequestMapping(path = "/movies", method = RequestMethod.POST)
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        ResponseEntity response = restTemplate.postForEntity(movieServiceUrl + "/movies", movie, Movie.class);
        return response;
    }
}
