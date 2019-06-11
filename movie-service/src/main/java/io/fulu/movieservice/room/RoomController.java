package io.fulu.movieservice.room;

import io.fulu.movieservice.movie.Movie;
import io.fulu.movieservice.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public void addRoom(@RequestBody Room room) {
        roomService.saveRoom(room);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable long id) {
        return roomService.getRoomById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateRoom(@PathVariable long id, @RequestBody Room room) {
        room.setId(id);
        roomService.saveRoom(room);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteRoom(@PathVariable long id) {
        return roomService.deleteRoom(id);
    }
}
