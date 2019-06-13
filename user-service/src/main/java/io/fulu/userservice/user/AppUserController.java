package io.fulu.userservice.user;

import io.fulu.userservice.user.ban.Ban;
import io.fulu.userservice.user.ban.BanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    AppUserService appUserService;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public AppUserDto signUp(@RequestBody AppUserDto userDto) {
        AppUser user = appUserService.signUp(dtoToEntity(userDto));
        return entityToDto(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AppUserDto> getUsers() {
        return appUserService.getUsers().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AppUserDto getUser(@PathVariable long id) {
        return entityToDto(appUserService.getUser(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AppUserDto updateUser(@PathVariable long id, @RequestBody AppUserDto userDto) {
        userDto.setId(id);
        AppUser user = appUserService.updateUser(dtoToEntity(userDto));
        return entityToDto(user);
    }

    @RequestMapping(value = "/{id}/ban", method = RequestMethod.POST)
    public boolean banUser(@PathVariable long id, @RequestBody BanDto banDto) {
        Ban ban = new Ban();
        ban.setAdmin(appUserService.getUser(banDto.getAdminId()));
        ban.setUser(appUserService.getUser(id));
        ban.setBegins(banDto.getBegins());
        ban.setDuration(banDto.getDuration());

        appUserService.banUser(id, ban);
        return true;
    }

    private AppUser dtoToEntity(AppUserDto userDto) {
        AppUser user = new AppUser();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setBookingCount(userDto.getBookingCount());
        return user;
    }

    private AppUserDto entityToDto(AppUser user) {
        AppUserDto userDto = new AppUserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setStatus(user.getStatus());
        userDto.setRole(user.getRole().getName());
        userDto.setBanned(false);
        userDto.setBookingCount(user.getBookingCount());
        return userDto;
    }
}
