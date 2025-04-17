package org.example.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @class Booking
 * @brief Сущность, представляющая бронирование номера в отеле.
 * @details Содержит информацию о бронировании, включая данные пользователя,
 * информацию о номере, даты проживания и статус бронирования.
 *
 * @entity Аннотация JPA для сопоставления с таблицей "Bookings"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "Bookings")
public class Booking {

    /**
     * @brief Уникальный идентификатор бронирования.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Пользователь, оформивший бронирование.
     * @details Связь "многие к одному" с таблицей пользователей.
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Связь с пользователем

    /**
     * @brief Имя клиента.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * @brief Фамилия клиента.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * @brief Контактный номер телефона клиента.
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * @brief Забронированный номер.
     * @details Связь "многие к одному" с сущностью Room.
     * @see Room
     */
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;  // Связь с комнатой

    /**
     * @brief Дата заезда.
     */
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;  // Дата заезда

    /**
     * @brief Дата выезда.
     */
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;  // Дата выезда

    /**
     * @brief Итоговая стоимость бронирования.
     */
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;  // Итоговая цена бронирования

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private BookingStatus status = BookingStatus.PENDING;  // Статус бронирования (CONFIRMED, CANCELLED, PENDING)

    /**
     * @brief Статус бронирования.
     * @details Например: PENDING, CONFIRMED, CANCELLED.
     * @see BookingStatus
     */
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private BookingStatus status;

    /**
     * @brief Дата и время создания бронирования.
     */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  // Дата создания бронирования

    // Конструкторы, геттеры и сеттеры

    /**
     * Конструктор по умолчанию.
     */
    public Booking() {}

    /**
     * Конструктор с параметрами.
     *
     * @param user Пользователь
     * @param firstName Имя
     * @param lastName Фамилия
     * @param phoneNumber Телефон
     * @param room Комната
     * @param checkInDate Дата заезда
     * @param checkOutDate Дата выезда
     * @param totalPrice Общая стоимость
     * @param status Статус бронирования
     */
    public Booking(User user, String firstName, String lastName,
                   String phoneNumber, Room room, LocalDate checkInDate,
                   LocalDate checkOutDate, Double totalPrice, BookingStatus status) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Устанавливаем текущую дату и время
    }

//    public Booking(User user, Room room, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal totalPrice, BookingStatus status) {
//        this.user = user;
//        this.room = room;
//        this.checkInDate = checkInDate;
//        this.checkOutDate = checkOutDate;
//        this.totalPrice = totalPrice;
//        this.status = status; //BookingStatus.PENDING;
//        this.createdAt = LocalDateTime.now(); // Устанавливаем текущую дату и время
//    }

    /** @return ID бронирования */
    public Long getId() {
        return id;
    }

    /** @param id Устанавливает ID бронирования */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return Пользователь, сделавший бронирование */
    public User getUser() {
        return user;
    }

    /** @param user Устанавливает пользователя */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return Имя клиента */
    public String getFirstName() {
        return firstName;
    }

    /** @param firstName Устанавливает имя клиента */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return Фамилия клиента */
    public String getLastName() {
        return lastName;
    }

    /** @param lastName Устанавливает фамилию клиента */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return Номер телефона клиента */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber Устанавливает номер телефона клиента */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return Комната, связанная с бронированием */
    public Room getRoom() {
        return room;
    }

    /** @param room Устанавливает комнату */
    public void setRoom(Room room) {
        this.room = room;
    }

    /** @return Дата заезда */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /** @param checkInDate Устанавливает дату заезда */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /** @return Дата выезда */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /** @param checkOutDate Устанавливает дату выезда */
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /** @return Итоговая цена бронирования */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /** @param totalPrice Устанавливает итоговую цену */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** @return Статус бронирования */
    public BookingStatus getStatus() {
        return status;
    }

    /** @param status Устанавливает статус бронирования */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /** @return Дата и время создания бронирования */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt Устанавливает дату и время создания */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @brief Преобразует объект в строковое представление.
     * @return Строка с описанием бронирования
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + id +
                ", user=" + user +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}