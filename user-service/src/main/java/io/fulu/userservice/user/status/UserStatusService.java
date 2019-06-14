package io.fulu.userservice.user.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusService {
    @Autowired
    UserStatusRepository userStatusRepository;


    public List<UserStatus> getStatus() {
        return userStatusRepository.findAll();
    }

//    public UserStatus findByName(String name) {
//        return userStatusRepository.findb
//    }

    public UserStatus saveStatus(UserStatus status) {
        userStatusRepository.save(status);
        return status;
    }

    public UserStatus getStatus(long id) {
        return userStatusRepository.findById(id).orElse(new UserStatus());
    }

    public boolean deleteStatus(long id) {
        try {
            userStatusRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
