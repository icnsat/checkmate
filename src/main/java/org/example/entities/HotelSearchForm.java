package org.example.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class HotelSearchForm {
    @NotNull(message = "Город не может быть пустым")
    private String city;

    @NotNull(message = "Укажите дату заезда")
    private LocalDate checkInDate;

    @NotNull(message = "Укажите дату выезда")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Количество взрослых должно быть не менее 1")
    private int adults;

    public HotelSearchForm() {}

    public HotelSearchForm(String city, LocalDate checkInDate, LocalDate checkOutDate, int adults) {
        this.city = city;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adults = adults;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }
}
