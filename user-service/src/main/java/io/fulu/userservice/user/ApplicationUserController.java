package io.fulu.userservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
    public List<ApplicationUser> getUsers() {
        return applicationUserService.getUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApplicationUser getUser(@PathVariable long id) {
        return applicationUserService.getUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable long id, @RequestBody ApplicationUser user) {
        user.setId(id);
        applicationUserService.updateUser(user);
    }
}
