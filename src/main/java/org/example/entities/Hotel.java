package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "country", nullable = false)
//    private String country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "rating", nullable = false, columnDefinition = "DECIMAL(2,1) CHECK (rating BETWEEN 0 AND 5)")
    private Double rating;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;


    // Конструкторы, геттеры и сеттеры
    public Hotel() {}

    public Hotel(String name, /*String country,*/ City city, String address, Double rating, User manager) {
        this.name = name;
        //this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.manager = manager;
    }

    public Hotel(String name, /*String country,*/ City city, String address, Double rating, List<Room> rooms, User manager) {
        this.name = name;
        //this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.rooms = rooms;
        this.manager = manager;
    }

    public Hotel(String name, City city, String address, Double rating, String photo, User manager) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.photo = photo;
        this.manager = manager;
    }

    public Hotel(String name, City city, String address, Double rating, String photo, List<Room> rooms, User manager) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.photo = photo;
        this.rooms = rooms;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
