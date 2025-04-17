package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * @brief Сущность, представляющая отель.
 * @details Хранит информацию об отеле, включая название, город, адрес, рейтинг, фото, номера и менеджера.
 *
 * @entity Аннотация JPA для сопоставления с таблицей "hotels"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "hotels")
public class Hotel {

    /**
     * @brief Уникальный идентификатор отеля.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Название отеля.
     */
    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "country", nullable = false)
//    private String country;

    /**
     * @brief Город, в котором расположен отель.
     * @manytoone Связь с сущностью City.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /**
     * @brief Адрес отеля.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * @brief Рейтинг отеля от 0 до 5.
     */
    @Column(name = "rating", nullable = false, columnDefinition = "DECIMAL(2,1) CHECK (rating BETWEEN 0 AND 5)")
    private Double rating;

    /**
     * @brief URL или путь к фотографии отеля.
     */
    @Column(name = "photo")
    private String photo;

    /**
     * @brief Список комнат, связанных с отелем.
     * @onetomany mappedBy = "hotel"
     */
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    /**
     * @brief Менеджер отеля.
     * @manytoone Связь с сущностью User.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;


    // Конструкторы, геттеры и сеттеры
    /**
     * @brief Конструктор без параметров.
     * @details Используется JPA для создания пустого объекта Hotel.
     */
    public Hotel() {}

    /**
     * @brief Конструктор отеля без фото и списка комнат.
     * @param name Название отеля.
     * @param city Город, где находится отель.
     * @param address Адрес отеля.
     * @param rating Рейтинг отеля.
     * @param manager Менеджер отеля.
     */
    public Hotel(String name, /*String country,*/ City city, String address, Double rating, User manager) {
        this.name = name;
        //this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.manager = manager;
    }

    /**
     * @brief Конструктор отеля со списком комнат, без фото.
     * @param name Название отеля.
     * @param city Город, где находится отель.
     * @param address Адрес отеля.
     * @param rating Рейтинг отеля.
     * @param rooms Список комнат в отеле.
     * @param manager Менеджер отеля.
     */
    public Hotel(String name, /*String country,*/ City city, String address, Double rating, List<Room> rooms, User manager) {
        this.name = name;
        //this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.rooms = rooms;
        this.manager = manager;
    }

    /**
     * @brief Конструктор отеля с фото, без списка комнат.
     * @param name Название отеля.
     * @param city Город, где находится отель.
     * @param address Адрес отеля.
     * @param rating Рейтинг отеля.
     * @param photo Путь или ссылка на фото отеля.
     * @param manager Менеджер отеля.
     */
    public Hotel(String name, City city, String address, Double rating, String photo, User manager) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.photo = photo;
        this.manager = manager;
    }

    /**
     * @brief Конструктор отеля с фото и списком комнат.
     * @param name Название отеля.
     * @param city Город, где находится отель.
     * @param address Адрес отеля.
     * @param rating Рейтинг отеля.
     * @param photo Путь или ссылка на фото отеля.
     * @param rooms Список комнат в отеле.
     * @param manager Менеджер отеля.
     */
    public Hotel(String name, City city, String address, Double rating, String photo, List<Room> rooms, User manager) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.photo = photo;
        this.rooms = rooms;
        this.manager = manager;
    }

    /**
     * @brief Получить ID отеля.
     * @return Уникальный идентификатор отеля.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID отеля.
     * @param id Идентификатор отеля.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить название отеля.
     * @return Название отеля.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Установить название отеля.
     * @param name Название отеля.
     */
    public void setName(String name) {
        this.name = name;
    }

//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }


    /**
     * @brief Получить город отеля.
     * @return Название города.
     */
    public City getCity() {
        return city;
    }

    /**
     * @brief Установить город отеля.
     * @param city Название города.
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @brief Получить адрес отеля.
     * @return Адрес отеля.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @brief Установить адрес отеля.
     * @param address Адрес отеля.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @brief Получить рейтинг отеля.
     * @return Рейтинг отеля.
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @brief Установить рейтинг отеля.
     * @param rating Рейтинг отеля.
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * @brief Получить фото отеля.
     * @return Фото отеля.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @brief Установить фото отеля.
     * @param photo Фото отеля.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @brief Получить номера отеля.
     * @return Номера отеля.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @brief Установить номера отеля.
     * @param rooms Номера отеля.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @brief Получить менеджера отеля.
     * @return Менеджер отеля.
     */
    public User getManager() {
        return manager;
    }

    /**
     * @brief Установить менеджера отеля.
     * @param manager Менеджер отеля.
     */
    public void setManager(User manager) {
        this.manager = manager;
    }
}