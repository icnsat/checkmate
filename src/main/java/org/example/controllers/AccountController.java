package org.example.controllers;

import org.example.entities.Booking;
import org.example.entities.User;
import org.example.services.BookingService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @class AccountController
 * @brief Контроллер для работы с личным кабинетом пользователя.
 *
 * Обрабатывает запросы, связанные с отображением информации в личном кабинете пользователя.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Controller
public class AccountController {
    private UserService userService;
    private BookingService bookingService;

    /**
     * @brief Конструктор с внедрением зависимостей.
     * @param userService Сервис для работы с пользователями
     * @param bookingService Сервис для работы с бронированиями
     */
    @Autowired
    public AccountController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    /**
     * @brief Обрабатывает запрос на отображение личного кабинета.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения (account.html)
     *
     * @details Метод выполняет:
     * 1. Получение текущего пользователя через userService
     * 2. Получение списка бронирований пользователя через bookingService
     * 3. Добавление данных в модель:
     *    - Атрибут "user" с информацией о пользователе
     *    - Атрибут "bookings" со списком бронирований
     * 4. Возвращает имя шаблона для отображения
     *
     * @note Требует аутентификации пользователя
     * @warning Логирование ошибок должно быть реализовано в сервисах
     */
    @GetMapping("/account")
    public String account(Model model) {
        User user = userService.getUser();
        List<Booking> bookings = bookingService.getBookingsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);

        return "account";
    }

}