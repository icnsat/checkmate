package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.entities.Hotel;
import org.example.entities.HotelSearchForm;
import org.example.entities.Room;
import org.example.services.HotelService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {
    private final HotelService hotelService;

    @Autowired
    public HomeController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);


    @GetMapping("/")
    public String homepage(Model model) {
        return "redirect:/search_homepage";
    }

    @GetMapping("/search_homepage")
    public String showSearchForm(Model model) {
        // Список популярных городов
        List<String> cities = List.of("Москва", "Санкт-Петербург", "Сочи", "Эсто-Садок");
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

    @PostMapping("/search")
    public String searchHotels(
            @ModelAttribute("hotelSearchForm") HotelSearchForm hotelSearchForm,
            /*HttpSession session,*/
            Model model) {

        //session.setAttribute("hotelSearchForm", hotelSearchForm);

        // Добавляем доступные города для поиска
        List<String> cities = List.of("Москва", "Санкт-Петербург", "Сочи", "Эсто-Садок");
        model.addAttribute("cities", cities);
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

        // Добавление данных в модель
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
//        model.addAttribute("hotelSearchForm", hotelSearchForm);

        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("adults", adults);

        return "search_rooms";
    }

}
