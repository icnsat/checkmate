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

@Controller
public class AccountController {
    private UserService userService;
    private BookingService bookingService;

    @Autowired
    public AccountController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @GetMapping("/account")
    public String account(Model model) {
        User user = userService.getUser();
        List<Booking> bookings = bookingService.getBookingsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);

        return "account";
    }

}
