package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.entities.*;
import org.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @class BookingController
 * @brief Контроллер для обработки операций бронирования номеров.
 *
 * Обрабатывает весь процесс бронирования от выбора номера до подтверждения.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Controller
public class BookingController {

    private HotelService hotelService;
    private BookingService bookingService;
    private CityService cityService;
    private RoomService roomService;
    private UserService userService;

    /**
     * @brief Конструктор с внедрением зависимостей.
     * @param hotelService Сервис работы с отелями
     * @param bookingService Сервис работы с бронированиями
     * @param cityService Сервис работы с городами
     * @param roomService Сервис работы с номерами
     * @param userService Сервис работы с пользователями
     */
    @Autowired
    public BookingController(HotelService hotelService,
                             BookingService bookingService,
                             CityService cityService,
                             RoomService roomService,
                             UserService userService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
        this.cityService = cityService;
        this.roomService = roomService;
        this.userService = userService;
    }

    /**
     * @brief Обрабатывает запрос на бронирование номера.
     * @param roomId ID номера
     * @param checkInDate Дата заезда (формат ISO DATE)
     * @param checkOutDate Дата выезда (формат ISO DATE)
     * @param adults Количество взрослых
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения
     *
     * @details Метод:
     * 1. Проверяет не заблокирован ли пользователь
     * 2. Получает информацию о номере, отеле и городе
     * 3. Добавляет данные в модель:
     *    - hotel: информация об отеле
     *    - room: информация о номере
     *    - city: информация о городе
     *    - checkInDate: дата заезда
     *    - checkOutDate: дата выезда
     *    - adults: количество гостей
     *
     * @note Доступен по POST /room/booking
     * @warning Возвращает booking_fail если пользователь заблокирован
     */
    @PostMapping("/room/booking")
    public String handleRoomBooking(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam int adults,
            Model model) {

        boolean userIsBlocked = userService.getUser().isIsBlocked();
        if (userIsBlocked) {
            return "booking_fail"; // аккаунт заблокирован
        }

        // Получение информации о номере
        Room room = roomService.getRoomById(roomId);
        Hotel hotel = hotelService.getHotelByRoomId(roomId);
        City city = cityService.getCityById(hotel.getCity().getId());

        // Передача данных на страницу бронирования
        model.addAttribute("hotel", hotel);
        model.addAttribute("room", room);
        model.addAttribute("city", city);
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("adults", adults);

        return "booking_form"; // Имя шаблона для формы бронирования
    }

    /**
     * @brief Подтверждает бронирование номера.
     * @param roomId ID номера
     * @param checkInDate Дата заезда (формат ISO DATE)
     * @param checkOutDate Дата выезда (формат ISO DATE)
     * @param adults Количество взрослых
     * @param totalPrice Общая стоимость
     * @param firstName Имя гостя
     * @param lastName Фамилия гостя
     * @param phone Телефон гостя
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения успешного бронирования
     *
     * @details Метод:
     * 1. Создает бронирование через bookingService
     * 2. Добавляет сообщение об успехе в модель
     * 3. Возвращает страницу подтверждения
     *
     * @note Доступен по POST /booking/confirm
     */
    @PostMapping("/booking/confirm")
    public String confirmBooking(
            // @AuthenticationPrincipal CustomUserDetails customUserDetails),
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam int adults,
            @RequestParam Double totalPrice,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            Model model) {

        // Обработка бронирования
        bookingService.createBooking(roomId, checkInDate, checkOutDate, adults, totalPrice, firstName, lastName, phone);

        model.addAttribute("message", "Бронирование успешно выполнено!");

        return "booking_success"; // Имя страницы успеха
    }

}

