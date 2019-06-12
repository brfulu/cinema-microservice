package io.fulu.userservice.user;

import io.fulu.userservice.user.ban.Ban;
import io.fulu.userservice.user.role.UserRole;
import io.fulu.userservice.user.role.UserRoleRepository;
import io.fulu.userservice.user.status.UserStatus;
import io.fulu.userservice.user.status.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationUserService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean signUp(ApplicationUser user) {
        List<Ban> banHistory = new ArrayList<>();

        UserStatus status = new UserStatus("GOLD", 10, 20, 0.2);
        userStatusRepository.save(status );

        UserRole role = new UserRole("admin", "ovo je admin");
        userRoleRepository.save(role);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBookingCount(0);
        user.setRole(role);
        user.setStatus(status);
        user.setBanHistory(banHistory);
        applicationUserRepository.save(user);

        return true;
    }

    public List<ApplicationUser> getUsers() {
        return applicationUserRepository.findAll();
    }

    public ApplicationUser getUser(long id) {
        return applicationUserRepository.findById(id).orElse(null);
    }

    public void updateUser(ApplicationUser user) {
        applicationUserRepository.save(user);
    }
}
