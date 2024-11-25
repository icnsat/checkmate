package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;  // Связь с отелем

    @Column(name = "room_type", nullable = false)
    private String roomType;  // Тип комнаты (Стандарт, Люкс и т. д.)

    @Column(name = "capacity", nullable = false)
    private int capacity;  // Вместимость комнаты

    @Column(name = "price", nullable = false)
    private Double price;  // Цена за ночь

    @Column(name = "description", nullable = false)
    private String description;  // Описание

    // @Column(name = "available", nullable = false)
    // private boolean available = true;  // Доступность комнаты



    // Конструкторы, геттеры и сеттеры

    public Room() {
    }

    public Room(Hotel hotel, String roomType, int capacity, Double price, String description) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    public boolean isAvailable() {
//        return available;
//    }
//
//    public void setAvailable(boolean available) {
//        this.available = available;
//    }


    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + id +
                ", roomType='" + roomType + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                ", description=" + description +
                // ", available=" + available +
                '}';
    }
}
