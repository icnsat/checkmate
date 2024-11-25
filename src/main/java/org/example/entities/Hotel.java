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

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "rating", nullable = false, columnDefinition = "DECIMAL(2,1) CHECK (rating BETWEEN 0 AND 5)")
    private Double rating;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;



    // Конструкторы, геттеры и сеттеры
    public Hotel() {}

    public Hotel(String name, String country, String city, String address, Double rating) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
    }

    public Hotel(String name, String country, String city, String address, Double rating, List<Room> rooms) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.rooms = rooms;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
