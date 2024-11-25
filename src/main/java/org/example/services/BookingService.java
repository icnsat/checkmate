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

    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository,
                          BookingStatusRepository bookingStatusRepository,
                          UserService userService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.userService = userService;
    }

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

        Booking booking = new Booking(user,
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

    public List<Booking> getBookingsByUser(User user){
        return bookingRepository.findAllByUser(user);
    }

}
