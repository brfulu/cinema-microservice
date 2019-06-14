package io.fulu.userservice.user;

import io.fulu.userservice.user.ban.Ban;
import io.fulu.userservice.user.ban.BanDto;
import io.fulu.userservice.user.ban.BanRepository;
import io.fulu.userservice.user.role.UserRole;
import io.fulu.userservice.user.role.UserRoleRepository;
import io.fulu.userservice.user.status.UserStatus;
import io.fulu.userservice.user.status.UserStatusRepository;
import io.fulu.userservice.user.status.UserStatusService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private BanRepository banRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUser signUp(AppUser user) {
        List<Ban> banHistory = new ArrayList<>();

        UserStatus status = userStatusService.getStatus(1);

        UserRole role = new UserRole("USER", "ovo je user");
        List<String> adminUsernames = Arrays.asList("fulu", "branko");
        if (adminUsernames.indexOf(user.getUsername()) > -1) {
            role = new UserRole("ADMIN", "ovo je admin");
            status = null;
        }
        userRoleRepository.save(role);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBookingCount(0);
        user.setRole(role);
        user.setStatus(status);
        user.setBanHistory(banHistory);
        appUserRepository.save(user);

        return user;
    }

    public Page<AppUser> getUsers(Pageable pageable) {
        return appUserRepository.findAll(pageable);
    }

    public AppUser getUser(long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser updateUser(AppUser newUser) {
        AppUser oldUser = appUserRepository.findById(newUser.getId()).orElse(new AppUser());
        newUser.setStatus(oldUser.getStatus());
        newUser.setRole(oldUser.getRole());
        newUser.setBanHistory(oldUser.getBanHistory());

        if (newUser.getPassword() == null) {
            newUser.setPassword(oldUser.getPassword());
        }

        appUserRepository.save(newUser);
        return newUser;
    }

    public void banUser(long id, Ban ban) {
        AppUser user = getUser(id);
        banRepository.save(ban);
        user.getBanHistory().add(ban);
        appUserRepository.save(user);
    }

    public boolean isBanned(String username) {
        AppUser user = findByUsername(username);
        return user != null && user.getBanHistory() != null && user.getBanHistory().size() > 0;
    }
}
