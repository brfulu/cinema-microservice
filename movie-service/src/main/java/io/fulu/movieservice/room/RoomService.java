package io.fulu.movieservice.room;

import io.fulu.movieservice.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public boolean deleteRoom(long id) {
        try {
            roomRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
