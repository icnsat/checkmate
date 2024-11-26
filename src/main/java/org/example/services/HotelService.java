package org.example.services;

import org.example.entities.Room;
import org.example.entities.Hotel;
import org.example.entities.HotelSearchForm;
import org.example.entities.User;
import org.example.repositories.BookingRepository;
import org.example.repositories.HotelRepository;
import org.example.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        // this.bookingRepository = bookingRepository;
    }

    public List<Hotel> findHotels(HotelSearchForm hotelSearchForm) {
        log.info("Find all users by city name from HotelSearchForm :)");

        return hotelRepository.findHotelsWithAvailableRooms(
                hotelSearchForm.getCityId(),
                hotelSearchForm.getCheckInDate(),
                hotelSearchForm.getCheckOutDate(),
                hotelSearchForm.getAdults()
        );
    }

    // Получение информации об отеле
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found :("));
    }

    // Поиск доступных номеров
    public List<Room> findAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate, int adults) {
        return roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate, adults);
    }

    public Hotel getHotelByRoomId(Long roomId) {
        return roomRepository.findHotelByRoomId(roomId);
    }

    public List<Hotel> getHotelsByManager(User currentUser) {
        return hotelRepository.findAllByManager(currentUser);
    }

    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Hotel hotel) {
        hotelRepository.delete(hotel);
    }
}
