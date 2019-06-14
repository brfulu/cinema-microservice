package io.fulu.userservice.user.status;

import io.fulu.userservice.user.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class UserStatusController {
    @Autowired
    UserStatusService userStatusService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserStatus> getStatus() {
        return userStatusService.getStatus();
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserStatus addStatus(@RequestBody UserStatus status) {
        return userStatusService.saveStatus(status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserStatus updateStatus(@PathVariable long id, @RequestBody UserStatus userStatus) {
        userStatus.setId(id);
        return userStatusService.saveStatus(userStatus);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteStatus(@PathVariable long id) {
        return userStatusService.deleteStatus(id);
    }
}
