package org.example.entities;

import jakarta.persistence.*;

/**
 * @class BookingStatus
 * @brief Сущность, представляющая статус бронирования.
 * @details Представляет статус, который может быть присвоен бронированию (например, PENDING, CONFIRMED, CANCELLED).
 *
 * @entity Аннотация JPA для сопоставления с таблицей "booking_statuses"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "booking_statuses")
public class BookingStatus {

    /**
     * @brief Уникальный идентификатор статуса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Название статуса.
     * @details Должно быть уникальным, например: CONFIRMED, CANCELLED.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * @brief Описание статуса.
     * @details Может содержать дополнительную информацию, отображаемую в UI или для логирования.
     */
    @Column(name = "description")
    private String description;


    // Геттеры, сеттеры, конструкторы
    /**
     * @brief Конструктор по умолчанию.
     */
    public BookingStatus() {}

    /**
     * @brief Конструктор с параметрами.
     * @param name Название статуса.
     * @param description Описание статуса.
     */
    public BookingStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @brief Получить ID статуса.
     * @return Уникальный идентификатор статуса.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID статуса.
     * @param id Идентификатор статуса.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить название статуса.
     * @return Название.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Установить название статуса.
     * @param name Название.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Получить описание статуса.
     * @return Текст описания.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @brief Установить описание статуса.
     * @param description Текст описания.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}