package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.entities.City;
import org.example.entities.Hotel;
import org.example.entities.HotelSearchForm;
import org.example.entities.Room;
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

@Controller
public class HomeController {
    private final HotelService hotelService;
    private final CityService cityService;
    @Autowired
    public HomeController(HotelService hotelService, CityService cityService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);


    @GetMapping("/")
    public String homepage(Model model) {
        return "redirect:/search_homepage";
    }

    @GetMapping("/search_homepage")
    public String showSearchForm(Model model) {
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
