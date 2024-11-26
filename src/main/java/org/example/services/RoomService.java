package org.example.services;

import org.example.entities.Hotel;
import org.example.entities.Room;
import org.example.repositories.HotelRepository;
import org.example.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    public List<Room> getRoomsByHotel(Hotel hotel) {
        return roomRepository.findAllByHotel(hotel);
    }


    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public List<Room> findByHotel(Hotel hotel) {
        return roomRepository.findAllByHotel(hotel);

    }

    public void deleteByHotel(Hotel hotel) {
        List<Room> rooms = roomRepository.findAllByHotel(hotel);

        if (rooms != null && !rooms.isEmpty()) {
            roomRepository.deleteAll(rooms);
        }
    }

}
