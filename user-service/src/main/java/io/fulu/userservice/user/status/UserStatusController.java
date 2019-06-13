package io.fulu.userservice.user.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class UserStatusController {
    @Autowired
    UserStatusService userStatusService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<UserStatus> getStatus() {
        return userStatusService.getStatus();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserStatus addStatus(@RequestBody UserStatus status) {
        return userStatusService.addStatus(status);
    }

}
