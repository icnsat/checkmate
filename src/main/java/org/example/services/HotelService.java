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

/**
 * @brief Сервис для работы с отелями и номерами.
 * @details Предоставляет методы для поиска отелей, доступных номеров,
 * получения информации об отеле и управления данными отелей.
 */
@Service
public class HotelService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    /**
     * @brief Конструктор класса HotelService.
     * @param hotelRepository Репозиторий отелей.
     * @param roomRepository Репозиторий номеров.
     */
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        // this.bookingRepository = bookingRepository;
    }

    /**
     * @brief Поиск отелей, удовлетворяющих параметрам формы поиска.
     * @param hotelSearchForm Форма поиска отеля.
     * @return Список отелей, подходящих под заданные параметры.
     */
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
    /**
     * @brief Получение информации об отеле по его идентификатору.
     * @param id Идентификатор отеля.
     * @return Объект отеля.
     * @throws RuntimeException если отель не найден.
     */
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found :("));
    }

    // Поиск доступных номеров
    /**
     * @brief Поиск доступных номеров в отеле.
     * @param hotelId Идентификатор отеля.
     * @param checkInDate Дата заезда.
     * @param checkOutDate Дата выезда.
     * @param adults Количество взрослых.
     * @return Список доступных номеров.
     */
    public List<Room> findAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate, int adults) {
        return roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate, adults);
    }

    /**
     * @brief Получение отеля по идентификатору номера.
     * @param roomId Идентификатор номера.
     * @return Объект отеля, к которому относится номер.
     */
    public Hotel getHotelByRoomId(Long roomId) {
        return roomRepository.findHotelByRoomId(roomId);
    }

    /**
     * @brief Получение отелей, которыми управляет конкретный менеджер.
     * @param currentUser Пользователь (менеджер).
     * @return Список отелей, назначенных менеджеру.
     */
    public List<Hotel> getHotelsByManager(User currentUser) {
        return hotelRepository.findAllByManager(currentUser);
    }

    /**
     * @brief Сохранение или обновление информации об отеле.
     * @param hotel Отель для сохранения.
     */
    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    /**
     * @brief Удаление отеля.
     * @param hotel Отель, который нужно удалить.
     */
    public void deleteHotel(Hotel hotel) {
        hotelRepository.delete(hotel);
    }
}