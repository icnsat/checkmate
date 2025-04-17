package org.example.entities;

import jakarta.persistence.*;

/**
 * @brief Сущность, представляющая роль пользователя в системе.
 * @details Определяет тип доступа и полномочий (например, ADMIN, MANAGER, USER).
 *
 * @entity Аннотация JPA для сопоставления с таблицей "roles"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * @brief Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Название роли.
     * @details Например: "ADMIN", "MANAGER", "USER". Уникально и обязательно.
     */
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;


    // Конструкторы, геттеры и сеттеры
    /**
     * @brief Пустой конструктор.
     * @details Необходим для JPA.
     */
    public Role() {}

    /**
     * @brief Конструктор с указанием названия роли.
     * @param roleName Название роли.
     */
    public Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @brief Получить ID роли.
     * @return Уникальный идентификатор роли.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID роли.
     * @param id Уникальный идентификатор роли.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить название роли.
     * @return Название роли.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @brief Установить название роли.
     * @param roleName Название роли.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @brief Возвращает строковое представление роли.
     * @return Название роли.
     */
    @Override
    public String toString() {
        return roleName;
    }
}