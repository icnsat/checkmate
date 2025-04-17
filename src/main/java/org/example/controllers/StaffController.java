package org.example.controllers;

import org.example.entities.*;
import org.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @class StaffController
 * @brief Контроллер для обработки запросов, связанные с действиями менеджеров отелей.
 *
 * Контроллер позволяет менеджерам просматривать отели, номера, бронирования и управлять ими.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    /** @brief Каталог загрузки изображений отелей и номеров */
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserService userService;
    private final HotelService hotelService;
    private RoomService roomService;
    private final BookingService bookingService;
    private final CityService cityService;

    /**
     * @brief Конструктор с внедрением зависимостей.
     * @param userService Сервис для работы с пользователями
     * @param hotelService Сервис для работы с отелями
     * @param bookingService Сервис для работы с бронированиями
     * @param roomService Сервис для работы с номерами
     * @param cityService Сервис для работы с городами
     */
    @Autowired
    public StaffController(UserService userService,
                           HotelService hotelService,
                           BookingService bookingService,
                           RoomService roomService,
                           CityService cityService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.cityService = cityService;
    }

    /**
     * @brief Отображает панель управления менеджера.
     * @param model Модель для передачи данных на фронтенд
     * @return Название HTML-шаблона
     */
    @GetMapping("/dashboard")
    public String managerDashboard(Model model) {
        // Получаем текущего пользователя
        User currentUser = userService.getUser();

        // Загружаем отели, связанные с этим менеджером
        List<Hotel> hotels = hotelService.getHotelsByManager(currentUser);

        // Загружаем бронирования для этих отелей
        List<Booking> bookings = bookingService.getBookingsByHotels(hotels);

        // Передаем данные в модель
        model.addAttribute("hotels", hotels);
        model.addAttribute("bookings", bookings);

        return "manager_dashboard";
    }
//
//    @GetMapping("/staff/bookings")
//    public String viewBookings(Model model, Authentication authentication) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        User currentUser = userDetails.getUser();
//
//        List<Booking> bookings = bookingService.getBookingsForManager(currentUser);
//        model.addAttribute("bookings", bookings);
//
//        return "manager/bookings";
//    }

    /**
     * @brief Отображает список номеров конкретного отеля.
     * @param hotelId Идентификатор отеля
     * @param model Модель для передачи данных
     * @return Название HTML-шаблона
     */
    @GetMapping("/hotel/rooms/{id}")
    public String viewHotelRooms(@PathVariable("id") Long hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Room> rooms = roomService.getRoomsByHotel(hotel);

        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);

        return "manager_hotel_rooms";
    }

    /**
     * @brief Обновляет данные отеля.
     * @param id Идентификатор отеля
     * @param name Название отеля
     * @param city Название города
     * @param country Страна
     * @param address Адрес
     * @param rating Рейтинг
     * @param photo Фото отеля
     * @return Редирект на дашборд
     * @throws IOException При ошибке загрузки файла
     */
    @PostMapping("/hotel/edit")
    public String editHotel(@RequestParam Long id,
                            @RequestParam String name,
                            @RequestParam String city,
                            @RequestParam String country,
                            @RequestParam String address,
                            @RequestParam Double rating,
//                            @RequestParam String description,
                            @RequestParam("photo") MultipartFile photo) throws IOException{
        Hotel hotel = hotelService.getHotelById(id);
        hotel.setName(name);
        hotel.setCity(cityService.findOrCreateByName(city, country));
        hotel.setAddress(address);
        hotel.setRating(rating);
//        hotel.setDescription(description);
        if (!photo.isEmpty()) {
            if (hotel.getPhoto() != null) {
                Path oldPhotoPath = Paths.get(uploadDir).resolve(hotel.getPhoto());
                Files.deleteIfExists(oldPhotoPath);
            }
            String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath); // Создаём папку, если её нет
            photo.transferTo(uploadPath.resolve(photoName).toFile());
            hotel.setPhoto(photoName);
        }
        hotelService.saveHotel(hotel);

        return "redirect:/staff/dashboard";
    }

    /**
     * @brief Добавляет новый отель.
     * @param name Название отеля
     * @param city Название города
     * @param country Страна
     * @param address Адрес
     * @param rating Рейтинг
     * @param photo Фото отеля
     * @return Редирект на дашборд
     * @throws IOException При ошибке загрузки файла
     */
    @PostMapping("/hotel/add")
    public String addHotel(@RequestParam String name,
                           @RequestParam String city,
                           @RequestParam String country,
                           @RequestParam String address,
                           @RequestParam Double rating,
                           @RequestParam("photo") MultipartFile photo) throws IOException {

        User manager = userService.getUser();

        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setCity(cityService.findOrCreateByName(city, country));
        hotel.setAddress(address);
        hotel.setRating(rating);

        // Сохраняем файл
        if (!photo.isEmpty()) {
            String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath); // Создаём папку, если её нет
            photo.transferTo(uploadPath.resolve(photoName).toFile());
            hotel.setPhoto(photoName);
        }

        hotel.setManager(manager);

        hotelService.saveHotel(hotel);

        return "redirect:/staff/dashboard";
    }

    /**
     * @brief Удаляет отель и связанные с ним данные.
     * @param id Идентификатор отеля
     * @return Редирект на дашборд
     */
    @PostMapping("/hotel/delete")
    public String deleteHotel(@RequestParam Long id) {
        // Получаем отель по ID
        Hotel hotel = hotelService.getHotelById(id);

        if (hotel != null) {
            // Удаляем бронирования всех комнат отеля
            roomService.findByHotel(hotel).forEach(room -> bookingService.deleteByRoom(room));

            // Удаляем все комнаты, связанные с этим отелем
            roomService.deleteByHotel(hotel);

            // Удаляем сам отель
            hotelService.deleteHotel(hotel);
        }

        return "redirect:/staff/dashboard";
    }

    /**
     * @brief Добавляет новый номер в отель.
     * @param hotelId Идентификатор отеля
     * @param roomType Тип номера
     * @param capacity Вместимость
     * @param price Цена за ночь
     * @param description Описание
     * @param photo Фото номера
     * @return Редирект к списку номеров отеля
     * @throws IOException При ошибке загрузки
     */
    @PostMapping("/room/add")
    public String addRoom(@RequestParam Long hotelId,
                          @RequestParam String roomType,
                          @RequestParam int capacity,
                          @RequestParam Double price,
                          @RequestParam String description,
                          @RequestParam("photo") MultipartFile photo) throws IOException {
        Room room = new Room();
        room.setHotel(hotelService.getHotelById(hotelId));
        room.setRoomType(roomType);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setDescription(description);

        // Обработка фотографии
        if (!photo.isEmpty()) {
            String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            photo.transferTo(uploadPath.resolve(photoName).toFile());
            room.setPhoto(photoName);
        }

        roomService.saveRoom(room);

        return "redirect:/staff/hotel/rooms/" + hotelId;
    }

    /**
     * @brief Редактирует информацию о номере.
     * @param id Идентификатор номера
     * @param hotelId Идентификатор отеля
     * @param roomType Тип номера
     * @param capacity Вместимость
     * @param price Цена за ночь
     * @param description Описание
     * @param photo Фото номера
     * @return Редирект к списку номеров отеля
     * @throws IOException При ошибке загрузки
     */
    @PostMapping("/room/edit")
    public String updateRoom(@RequestParam Long id,
                             @RequestParam Long hotelId,
                             @RequestParam String roomType,
                             @RequestParam int capacity,
                             @RequestParam Double price,
                             @RequestParam String description,
                             @RequestParam("photo") MultipartFile photo) throws IOException {
        Room room = roomService.getRoomById(id);

        room.setRoomType(roomType);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setDescription(description);

        // Замена существующего фото, если новое предоставлено
        if (!photo.isEmpty()) {
            // Удаление старого фото, если оно существует
            if (room.getPhoto() != null) {
                Path oldPhotoPath = Paths.get(uploadDir).resolve(room.getPhoto());
                Files.deleteIfExists(oldPhotoPath);
            }

            // Сохранение нового фото
            String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            photo.transferTo(uploadPath.resolve(photoName).toFile());
            room.setPhoto(photoName);
        }

        roomService.saveRoom(room);

        return "redirect:/staff/hotel/rooms/" + hotelId;
    }

    /**
     * @brief Удаляет номер и связанные с ним бронирования.
     * @param id Идентификатор номера
     * @return Редирект к списку номеров отеля
     */
    @PostMapping("/room/delete")
    public String deleteRoom(@RequestParam Long id) {
        Room room = roomService.getRoomById(id);

//        // Удаление фото, если существует
//        if (room.getPhoto() != null) {
//            Path photoPath = Paths.get("uploads").resolve(room.getPhoto());
//            try {
//                Files.deleteIfExists(photoPath);
//            } catch (IOException e) {
//                e.printStackTrace(); // Логируем ошибку удаления
//            }
//        }

        bookingService.deleteByRoom(room);
        roomService.deleteRoom(id);

        return "redirect:/staff/hotel/rooms/" + room.getHotel().getId();
    }


    // Отображение текущих бронирований
    /**
     * @brief Отображает список всех бронирований для менеджера.
     * @param model Модель с данными
     * @return HTML-шаблон со списком бронирований
     */
    @GetMapping("/booking")
    public String viewBookings(Model model) {
        // Получаем текущего пользователя
        User currentUser = userService.getUser();

        // Получить список отелей, к которым менеджер имеет доступ
        List<Hotel> managerHotels = hotelService.getHotelsByManager(currentUser);
        // Получить список всех бронирований для этих отелей
        List<Booking> bookings = bookingService.getBookingsByHotels(managerHotels);

        model.addAttribute("bookings", bookings);
        return "manager_bookings";
    }

    // Изменение статуса бронирования
    /**
     * @brief Изменяет статус бронирования.
     * @param bookingId Идентификатор бронирования
     * @param status Новый статус (CONFIRMED или CANCELLED)
     * @return Редирект к списку бронирований
     */
    @PostMapping("/booking/edit")
    public String editBookingStatus(@RequestParam Long bookingId,
                                    @RequestParam String status) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking != null && ("CONFIRMED".equalsIgnoreCase(status) || "CANCELLED".equalsIgnoreCase(status))) {
            booking.setStatus(bookingService.getBookingStatusByName(status.toUpperCase()));
            bookingService.updateBooking(booking);
        }
        return "redirect:/staff/booking";
    }

}