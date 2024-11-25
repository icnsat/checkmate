package org.example.services;

import org.example.entities.Room;
import org.example.entities.Hotel;
import org.example.entities.HotelSearchForm;
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
//    @Autowired
//    private final BookingRepository bookingRepository;


    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        // this.bookingRepository = bookingRepository;
    }

//    public boolean isAvailable(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
//        /** Если для отеля есть пересекающиеся бронирования, он недоступен **/
//        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(hotelId, checkInDate, checkOutDate);
//        return overlappingBookings.isEmpty();
//    }

    public List<Hotel> findHotels(HotelSearchForm hotelSearchForm) {
        log.info("Find all users by city name from HotelSearchForm :)");

        /** Убрали отдельно получение этих параметров:
         * hotelSearchForm.getCity() вместо city в hotelRepository.findByCity() и т.д. **/
        // String city = hotelSearchForm.getCity();
        // LocalDate checkInDate = hotelSearchForm.getCheckInDate();
        // LocalDate checkOutDate = hotelSearchForm.getCheckOutDate();
        // int adults = hotelSearchForm.getAdults();

//        /** Простая бизнес-логика: поиск отелей по городу **/
//        List<Hotel> hotels = hotelRepository.findByCity(hotelSearchForm.getCity());

//         (Опционально) можно добавить фильтрацию, если потребуется (например, по доступным датам)
//        hotels = hotels.stream()
//                .filter(hotel -> hotel.isAvailable(checkInDate, checkOutDate))
//                .collect(Collectors.toList());

//        /** Фильтруем отели по доступности **/
//        return hotels.stream()
//                .filter(hotel -> isAvailable(hotel.getId(), hotelSearchForm.getCheckInDate(), hotelSearchForm.getCheckOutDate()))
//                .collect(Collectors.toList());

        // return hotels;
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

    public Room getRoomById(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    public Hotel getHotelByRoomId(Long roomId) {
        return roomRepository.findHotelByRoomId(roomId);
    }
}
