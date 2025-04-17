package org.example.entities;

import jakarta.persistence.*;

/**
 * @brief Сущность, представляющая город, в котором расположен отель.
 * @details Используется для хранения названия города и страны, где находится отель.
 *
 * @entity Аннотация JPA для сопоставления с таблицей "cities"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "cities")
public class City {

    /**
     * @brief Уникальный идентификатор города.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Название города.
     * @details Должно быть уникальным в базе данных.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * @brief Название страны, в которой находится город.
     */
    @Column(name = "country", nullable = false)
    private String country;

    /**
     * @brief Конструктор по умолчанию.
     */
    public City() {
    }

    /**
     * @brief Конструктор с параметрами.
     * @param name Название города.
     * @param country Название страны.
     */
    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    /**
     * @brief Получить ID города.
     * @return Уникальный идентификатор города.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID города.
     * @param id Идентификатор города.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить название города.
     * @return Название города.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Установить название города.
     * @param name Название города.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Получить страну города.
     * @return Название страны.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @brief Установить страну города.
     * @param country Название страны.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}