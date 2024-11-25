package org.example.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Связь с пользователем


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;  // Связь с комнатой

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;  // Дата заезда

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;  // Дата выезда

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;  // Итоговая цена бронирования

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private BookingStatus status = BookingStatus.PENDING;  // Статус бронирования (CONFIRMED, CANCELLED, PENDING)

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private BookingStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  // Дата создания бронирования

    // Конструкторы, геттеры и сеттеры

    public Booking() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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
