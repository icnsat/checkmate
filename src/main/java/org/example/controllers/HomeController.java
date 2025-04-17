package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.entities.*;
import org.example.services.CityService;
import org.example.services.HotelService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @class HomeController
 * @brief Контроллер для главной страницы, поиска отелей, отображения информации об отеле и автодополнения городов.
 *
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Controller
public class HomeController {
    private final HotelService hotelService;
    private final CityService cityService;
    private final UserService userService;

    /**
     * @brief Конструктор с внедрением зависимостей.
     * @param hotelService Сервис работы с отелями
     * @param cityService Сервис работы с городами
     * @param userService Сервис работы с пользователями
     */
    @Autowired
    public HomeController(HotelService hotelService, CityService cityService, UserService userService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
        this.userService = userService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    /**
     * @brief Перенаправляет на страницу поиска отелей.
     * @param model Модель для передачи данных в шаблон
     * @return редирект на /search_homepage
     */
    @GetMapping("/")
    public String homepage(Model model) {
        return "redirect:/search_homepage";
    }

    /**
     * @brief Отображает форму поиска отелей с начальными значениями.
     * @param model Модель для передачи данных в шаблон
     * @return имя Thymeleaf-шаблона "search_homepage"
     */
    @GetMapping("/search_homepage")
    public String showSearchForm(Model model) {
//        User user = userService.getUser();
//        model.addAttribute("user", user);

        // Список популярных городов
        List<City> cities = cityService.getAllCities();//List.of("Москва", "Санкт-Петербург", "Сочи", "Эсто-Садок");
        model.addAttribute("cities", cities);

        // Получаем текущую дату
        LocalDate today = LocalDate.now();

        // Завтрашняя и послезавтрашняя даты
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        // Преобразование дат в строку (формат YYYY-MM-DD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String defaultCheckInDate = tomorrow.format(formatter);
        String defaultCheckOutDate = dayAfterTomorrow.format(formatter);
        String todayDate = today.format(formatter);


        // Параметры по умолчанию
        model.addAttribute("defaultAdults", 2);
        model.addAttribute("todayDate", todayDate); // Пример значения по умолчанию
        model.addAttribute("defaultCheckInDate", defaultCheckInDate); // Пример значения по умолчанию
        model.addAttribute("defaultCheckOutDate", defaultCheckOutDate);

        return "search_homepage"; // Имя шаблона Thymeleaf
    }

    /**
     * @brief Обрабатывает форму поиска отелей и возвращает результаты.
     * @param hotelSearchForm Объект формы поиска отелей
     * @param model Модель для передачи данных в шаблон
     * @return имя Thymeleaf-шаблона "search_results"
     */
    @PostMapping("/search")
    public String searchHotels(
            @ModelAttribute("hotelSearchForm") HotelSearchForm hotelSearchForm,
            /*HttpSession session,*/
            Model model) {

        //session.setAttribute("hotelSearchForm", hotelSearchForm);

        // Добавляем доступные города для поиска
        List<City> cities = cityService.getAllCities(); // Получаем все города
        model.addAttribute("cities", cities);

        City city = cityService.getCityById(hotelSearchForm.getCityId());
        model.addAttribute("city", city);


        // Получаем текущую дату
        LocalDate today = LocalDate.now();
        // Преобразование дат в строку (формат YYYY-MM-DD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = today.format(formatter);
        // Параметры по умолчанию
        model.addAttribute("todayDate", todayDate); // Пример значения по умолчанию


        // Фильтруем отели по городу
        List<Hotel> hotels = hotelService.findHotels(hotelSearchForm);

        // Передаем данные формы обратно в модель
        model.addAttribute("hotelSearchForm", hotelSearchForm);
        // Передаем отели в модель
        model.addAttribute("hotels", hotels);

        return "search_results"; // Имя HTML-шаблона для результатов
    }

    /**
     * @brief API-метод для поиска городов по строке запроса.
     * @param query Часть названия города
     * @return список городов, содержащих подстроку запроса
     */
    @GetMapping("/api/cities")
    @ResponseBody
    public List<City> searchCities(@RequestParam String query) {
        return cityService.searchCities(query); // Возвращаем города, которые содержат query
    }
//    public ResponseEntity<List<String>> getCities(@RequestParam String query) {
//        // Вызываем сервис, чтобы найти города по введенной строке
//        List<String> matchingCities = cityService.findCitiesByQuery(query);
//        return ResponseEntity.ok(matchingCities);
//    }

    /**
     * @brief Отображает подробности об отеле и доступных номерах на выбранные даты.
     * @param hotelId ID отеля
     * @param checkInDate Дата заезда
     * @param checkOutDate Дата выезда
     * @param adults Количество взрослых
     * @param model Модель для передачи данных в шаблон
     * @return имя Thymeleaf-шаблона "search_rooms"
     */
    @GetMapping("/hotel/{id}")
    public String getHotelDetails(
            @PathVariable("id") Long hotelId,
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("adults") int adults,
//            HttpSession session,
            Model model
    ) {

//        HotelSearchForm hotelSearchForm = (HotelSearchForm) session.getAttribute("hotelSearchForm");
//
//        // Если форма отсутствует, создаем новую (на случай прямого перехода)
//        if (hotelSearchForm == null) {
//            hotelSearchForm = new HotelSearchForm();
//            hotelSearchForm.setCheckInDate(LocalDate.now());
//            hotelSearchForm.setCheckOutDate(LocalDate.now().plusDays(1));
//            hotelSearchForm.setAdults(2); // Значение по умолчанию
//        }

        // Получение информации об отеле
        Hotel hotel = hotelService.getHotelById(hotelId);

        // Получение подходящих номеров
        List<Room> rooms = hotelService.findAvailableRooms(
                hotelId,
                checkInDate,
                checkOutDate,
                adults
//                hotelSearchForm.getCheckInDate(),
//                hotelSearchForm.getCheckOutDate(),
//                hotelSearchForm.getAdults()
        );

        City city = cityService.getCityById(hotel.getCity().getId());

        // Добавление данных в модель
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        model.addAttribute("city", city);
//        model.addAttribute("hotelSearchForm", hotelSearchForm);

        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("adults", adults);

        return "search_rooms";
    }

}