package org.example.services;

import org.example.entities.*;
import org.example.repositories.BookingRepository;
import org.example.repositories.BookingStatusRepository;
import org.example.repositories.HotelRepository;
import org.example.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @brief Сервис для управления бронированиями.
 * @details Этот класс отвечает за создание, получение, обновление и удаление бронирований.
 * Взаимодействует с репозиториями, связанными с сущностями Booking, Room, BookingStatus.
 */
@Service
public class BookingService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingStatusRepository bookingStatusRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;

    /**
     * @brief Конструктор BookingService с внедрением зависимостей.
     * @param bookingRepository Репозиторий бронирований.
     * @param roomRepository Репозиторий комнат.
     * @param bookingStatusRepository Репозиторий статусов бронирований.
     * @param userService Сервис для получения текущего пользователя.
     */
    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository,
                          BookingStatusRepository bookingStatusRepository,
                          UserService userService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.userService = userService;
    }

    /**
     * @brief Создание нового бронирования.
     * @param roomId ID комнаты.
     * @param checkInDate Дата заезда.
     * @param checkOutDate Дата выезда.
     * @param adults Количество взрослых.
     * @param totalPrice Итоговая цена бронирования.
     * @param firstName Имя клиента.
     * @param lastName Фамилия клиента.
     * @param phone Номер телефона клиента.
     */
    public void createBooking(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int adults,
            Double totalPrice,
            String firstName,
            String lastName,
            String phone) {

        User user = userService.getUser();

        Room room = roomRepository.findRoomById(roomId);
        BookingStatus bookingStatus = bookingStatusRepository.findBookingStatusByName("PENDING");
        // LocalDateTime createdAt = LocalDateTime.now(); // уже есть в конструкторе

        Booking booking = new Booking(
                user,
                firstName,
                lastName,
                phone,
                room,
                checkInDate,
                checkOutDate,
                totalPrice,
                bookingStatus
        );

        bookingRepository.save(booking);
        log.info("New booking added");
    }

    /**
     * @brief Получение всех бронирований пользователя.
     * @param user Пользователь.
     * @return Список бронирований, сделанных этим пользователем.
     */
    public List<Booking> getBookingsByUser(User user){
        return bookingRepository.findAllByUser(user);
    }

    /**
     * @brief Получение бронирований по списку отелей.
     * @param hotels Список отелей.
     * @return Список бронирований в указанных отелях.
     */
    public List<Booking> getBookingsByHotels(List<Hotel> hotels) {
        return bookingRepository.findByHotelIn(hotels);
    }

    /**
     * @brief Удаление бронирований, связанных с определённой комнатой.
     * @param room Комната, для которой удаляются бронирования.
     */
    public void deleteByRoom(Room room) {
        List<Booking> bookings = bookingRepository.findByRoom(room);

        if (bookings != null && !bookings.isEmpty()) {
            bookingRepository.deleteAll(bookings);
        }
    }

    /**
     * @brief Получение бронирования по его ID.
     * @param bookingId Идентификатор бронирования.
     * @return Объект Booking или null, если не найден.
     */
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findBookingById(bookingId);
    }

    /**
     * @brief Получение статуса бронирования по его имени.
     * @param name Имя статуса (например, "PENDING", "CONFIRMED").
     * @return Объект BookingStatus.
     */
    public BookingStatus getBookingStatusByName(String name) {
        return bookingStatusRepository.findBookingStatusByName(name);
    }

    /**
     * @brief Обновление информации о бронировании.
     * @param booking Обновлённый объект Booking.
     */
    public void updateBooking(Booking booking){
        bookingRepository.save(booking);
    }
}