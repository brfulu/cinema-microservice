package io.fulu.userservice.user;

import io.fulu.userservice.user.ban.Ban;
import io.fulu.userservice.user.ban.BanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    AppUserService appUserService;

//    @RequestMapping(value= "/**", method=RequestMethod.OPTIONS)
//    public void corsHeaders(HttpServletResponse response) {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
//        response.addHeader("Access-Control-Max-Age", "3600");
//    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public AppUserDto signUp(@RequestBody AppUserDto userDto) {
        AppUser user = appUserService.signUp(dtoToEntity(userDto));
        return entityToDto(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AppUserDto> getUsers(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("sortBy") Optional<String> sortBy,
                                     @RequestParam("username") Optional<String> username) {
        String sortField = sortBy.orElse("+username").substring(1);
        Sort sort = sortBy.orElse("+username").charAt(0) == '+' ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(1000), sort);

        return appUserService.getUsers(pageable).stream()
                .map(this::entityToDto)
                .filter(user -> user.getUsername().contains(username.orElse("")))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AppUserDto getUser(@PathVariable long id) {
        return entityToDto(appUserService.getUser(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AppUserDto> updateUser(@PathVariable long id, @RequestBody AppUserDto userDto) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appUserService.isBanned(username)) {
            return ResponseEntity.status(401).body(null);
        }
        AppUser user = appUserService.findByUsername(username);
//        if (user == null || user.getId() != id) {
//            return ResponseEntity.status(401).body(null);
//        }

        userDto.setId(id);
        user = appUserService.updateUser(dtoToEntity(userDto));
        return ResponseEntity.accepted().body(entityToDto(user));
    }

    @RequestMapping(value = "/{id}/ban", method = RequestMethod.POST)
    public boolean banUser(@PathVariable long id, @RequestBody BanDto banDto) {
        System.out.println("evoo meeeeeee");
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
        userDto.setBanned(appUserService.isBanned(user.getUsername()));
        userDto.setBookingCount(user.getBookingCount());
        return userDto;
    }

}
