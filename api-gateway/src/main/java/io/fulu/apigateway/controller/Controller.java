package io.fulu.apigateway.controller;

import io.fulu.apigateway.model.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    private final String userServiceUrl = "http://localhost:8083";
    private final String movieServiceUrl = "http://localhost:8082";
    private final String bookingServiceUrl = "http://localhost:8081";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody AppUserDto appUserDto) {
        restTemplate.postForLocation(userServiceUrl + "/login", appUserDto);
    }
}
