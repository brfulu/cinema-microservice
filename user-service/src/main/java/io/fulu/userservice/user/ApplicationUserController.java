package io.fulu.userservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {
    @Autowired
    ApplicationUserService applicationUserService;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody ApplicationUser user) {
        applicationUserService.signUp(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUsers() {
        return "Users";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getUser() {
        return "User";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String updateUser() {
        return "Updated user";
    }
}
