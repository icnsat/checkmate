package org.example.entities;

import jakarta.persistence.*;

/**Для аннотаций NotBlank**/
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @brief Сущность, представляющая пользователя системы.
 * @details Представляет зарегистрированного пользователя с ролью и статусом блокировки.
 *
 * @entity Аннотация JPA для сопоставления с таблицей "users"
 * @table Указывает имя таблицы в базе данных
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * @brief Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Email пользователя.
     * @details Должен быть корректным email-адресом. Поле уникально и обязательно.
     */
    @NotBlank(message = "Email обязателен")
    @Email(message = "Введите корректный email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * @brief Пароль пользователя.
     * @details Обязательное поле.
     */
    @NotBlank(message = "Пароль обязателен")
    //@Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    @Column(name = "password", nullable = false)
    private String password;

    //@Column(name = "first_name")
    //private String firstName;

    //@Column(name = "last_name")
    //private String lastName;

    //@Column(name = "phone_number")
    //private String phoneNumber;

    /**
     * @brief Роль пользователя.
     * @manytoone Многие пользователи могут иметь одну роль.
     * @joincolumn Связь с таблицей ролей.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * @brief Флаг блокировки пользователя.
     * @details Если true — пользователь заблокирован.
     */
    @Column(name = "is_blocked")
    private boolean isBlocked;


    // Конструкторы, геттеры и сеттеры
    /**
     * @brief Конструктор по умолчанию.
     */
    public User() {}

    /**
     * @brief Конструктор для создания пользователя с email, паролем и ролью.
     * @param email Email пользователя.
     * @param password Пароль.
     * @param role Роль (объект Role).
     */
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * @brief Конструктор с флагом блокировки.
     * @param email Email пользователя.
     * @param password Пароль.
     * @param role Роль.
     * @param is_blocked true, если пользователь заблокирован.
     */
    public User(String email, String password, Role role, boolean is_blocked) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.isBlocked = is_blocked;
    }

    /**
     * @brief Получить ID пользователя.
     * @return Уникальный идентификатор пользователя.
     */
    public Long getId() {
        return id;
    }

    /**
     * @brief Установить ID пользователя.
     * @param id Новый идентификатор.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @brief Получить email пользователя.
     * @return Email в виде строки.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Установить email пользователя.
     * @param email Новый email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Получить пароль пользователя.
     * @return Пароль в виде строки.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @brief Установить пароль пользователя.
     * @param password Новый пароль.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @brief Получить роль пользователя.
     * @return Объект Role, определяющий права пользователя.
     */
    public Role getRole() {
        return role;
    }

    /**
     * @brief Установить роль пользователя.
     * @param role Новый объект Role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @brief Проверить, заблокирован ли пользователь.
     * @return true, если пользователь заблокирован; false в противном случае.
     */
    public boolean isIsBlocked() {
        return isBlocked;
    }

    /**
     * @brief Установить статус блокировки пользователя.
     * @param is_blocked true — пользователь заблокирован; false — нет.
     */
    public void setIsBlocked(boolean is_blocked) {
        this.isBlocked = is_blocked;
    }
}