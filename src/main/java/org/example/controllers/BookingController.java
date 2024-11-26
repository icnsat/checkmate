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

@Controller
public class BookingController {

    private HotelService hotelService;
    private BookingService bookingService;
    private CityService cityService;
    private RoomService roomService;

    @Autowired
    public BookingController(HotelService hotelService,
                             BookingService bookingService,
                             CityService cityService,
                             RoomService roomService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
        this.cityService = cityService;
        this.roomService = roomService;
    }

    @PostMapping("/room/booking")
    public String handleRoomBooking(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam int adults,
            Model model) {

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


