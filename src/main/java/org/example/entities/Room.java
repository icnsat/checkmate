package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


/**
 * @brief Сущность, представляющая комнату в отеле.
 * @details Хранит информацию о типе комнаты, вместимости, цене, описании и связи с отелем.
 *
 * @entity Аннотация JPA для сопоставления с таблицей "rooms"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "rooms")
public class Room {

    /**
     * @brief Уникальный идентификатор комнаты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Отель, к которому принадлежит комната.
     * @manytoone
     * @joincolumn name = "hotel_id"
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;  // Связь с отелем

    /**
     * @brief Тип комнаты (например, Стандарт, Люкс).
     */
    @Column(name = "room_type", nullable = false)
    private String roomType;  // Тип комнаты (Стандарт, Люкс и т. д.)

    /**
     * @brief Вместимость комнаты (количество человек).
     */
    @Column(name = "capacity", nullable = false)
    private int capacity;  // Вместимость комнаты

    /**
     * @brief Цена за ночь.
     */
    @Column(name = "price", nullable = false)
    private Double price;  // Цена за ночь

    /**
     * @brief Описание комнаты.
     */
    @Column(name = "description", nullable = false)
    private String description;  // Описание

    /**
     * @brief Ссылка на фото комнаты.
     */
    @Column(name = "photo")
    private String photo;

    // @Column(name = "available", nullable = false)
    // private boolean available = true;  // Доступность комнаты



    // Конструкторы, геттеры и сеттеры

    /**
     * @brief Пустой конструктор.
     * @details Необходим для JPA.
     */
    public Room() {
    }

    /**
     * @brief Конструктор для создания комнаты без фото.
     * @param hotel Отель, к которому относится комната.
     * @param roomType Тип комнаты.
     * @param capacity Вместимость.
     * @param price Цена за ночь.
     * @param description Описание.
     */
    public Room(Hotel hotel, String roomType, int capacity, Double price, String description) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }

    /**
     * @brief Конструктор с указанием фото.
     * @param hotel Отель.
     * @param roomType Тип комнаты.
     * @param capacity Вместимость.
     * @param price Цена.
     * @param description Описание.
     * @param photo Фото комнаты.
     */
    public Room(Hotel hotel, String roomType, int capacity, Double price, String description, String photo) {
        this.hotel = hotel;
        this.roomType = roomType;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    /**
     * @brief Получить ID номера.
     * @return Уникальный идентификатор номера.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID номера.
     * @param id Идентификатор номера.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить отель номера.
     * @return Отель номера.
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * @brief Установить отель номера.
     * @param hotel Отель номера.
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * @brief Получить тип номера.
     * @return Тип номера.
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * @brief Установить тип номера.
     * @param roomType Тип номера.
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * @brief Получить вместимость номера.
     * @return Вместимость номера.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @brief Установить вместимость номера.
     * @param capacity Вместимость номера.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @brief Получить цену номера за ночь.
     * @return Цена номера за ночь.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @brief Установить цену номера за ночь.
     * @param price Цена номера за ночь.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @brief Получить описаиние номера.
     * @return Описание номера.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Установить описаиние номера.
     * @param description Описание номера.
     */
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


    /**
     * @brief Получить фото номера.
     * @return Фото номера.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @brief Установить фото номера.
     * @param photo Фото номера.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @brief Возвращает строковое представление комнаты.
     * @return Строка с основными характеристиками комнаты.
     */
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