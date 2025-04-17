package org.example.repositories;

import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @brief Репозиторий для управления сущностями User (Пользователи).
 * @details Содержит методы для работы с пользователями, включая поиск пользователей по email и извлечение всех пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @brief Найти пользователя по его email.
     * @param email Адрес электронной почты пользователя.
     * @return Пользователь с указанным email.
     */
    User findUserByEmail(String email);

    /**
     * @brief Найти пользователя по его email.
     * @param email Адрес электронной почты пользователя.
     * @return Optional объект, содержащий пользователя, если он существует, или пустой, если не найден.
     */
    Optional<User> findByEmail(String email);

    /**
     * @brief Получить список всех пользователей.
     * @return Список всех пользователей в базе данных.
     */
    List<User> findAll();
}