package org.example.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @brief Класс формы для поиска отелей.
 * @details Используется для передачи параметров поиска при запросе отелей.
 */
public class HotelSearchForm {

    /**
     * @brief Идентификатор города.
     * @details Указывает город, в котором производится поиск отеля.
     */
    @NotNull(message = "Город не может быть пустым")
    //private String city;
    private Long cityId;

    /**
     * @brief Дата заезда.
     * @details Пользователь должен указать дату начала проживания.
     */
    @NotNull(message = "Укажите дату заезда")
    private LocalDate checkInDate;

    /**
     * @brief Дата выезда.
     * @details Пользователь должен указать дату окончания проживания.
     */
    @NotNull(message = "Укажите дату выезда")
    private LocalDate checkOutDate;

    /**
     * @brief Количество взрослых гостей.
     * @details Минимум 1 взрослый должен быть указан.
     */
    @Min(value = 1, message = "Количество взрослых должно быть не менее 1")
    private int adults;

    /**
     * @brief Конструктор без параметров.
     * @details Необходим для сериализации и работы с формами.
     */
    public HotelSearchForm() {}

    /**
     * @brief Полный конструктор формы поиска отеля.
     * @param cityId Идентификатор города.
     * @param checkInDate Дата заезда.
     * @param checkOutDate Дата выезда.
     * @param adults Количество взрослых.
     */
    public HotelSearchForm(Long cityId, LocalDate checkInDate, LocalDate checkOutDate, int adults) {
        this.cityId = cityId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adults = adults;
    }

    /**
     * @brief Получить идентификатор города отеля.
     * @return Идентификатор города.
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @brief Установить идентификатор города отеля.
     * @param cityId Идентификатор города.
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * @brief Получить дату заезда.
     * @return Дата заезда.
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * @brief Установить дату заезда.
     * @param checkInDate Дата заезда.
     */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @brief Получить дату выезда.
     * @return Дата выезда.
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @brief Установить дату выезда.
     * @param checkOutDate Дата выезда.
     */
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @brief Получить количество гостей.
     * @return Количество гостей.
     */
    public int getAdults() {
        return adults;
    }

    /**
     * @brief Установить количество гостей.
     * @param adults Количество гостей.
     */
    public void setAdults(int adults) {
        this.adults = adults;
    }
}