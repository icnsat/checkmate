package org.example.repositories;

import org.example.entities.BookingStatus;
import org.example.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @brief Репозиторий для управления сущностями City (Города).
 * @details Обеспечивает интерфейс для операций с базой данных, связанных с городами.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * @brief Получить список всех городов.
     * @return Список всех объектов City.
     */
    List<City> findAll();

    /**
     * @brief Поиск городов по части имени (без учета регистра).
     * @param query Подстрока для поиска по имени города.
     * @return Список городов, имя которых содержит заданную подстроку.
     */
    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<City> findByNameContainingIgnoreCase(@Param("query") String query);

    /**
     * @brief Найти город по его уникальному идентификатору.
     * @param id Идентификатор города.
     * @return Объект City.
     */
    City findCityById(Long id);

    /**
     * @brief Найти город по имени (обернут в Optional).
     * @param trim Имя города.
     * @return Optional, содержащий объект City, если найден.
     */
    Optional<City> findByName(String trim);

    /**
     * @brief Найти город по имени (без Optional).
     * @param trim Имя города.
     * @return Объект City, если найден.
     */
    City findCityByName(String trim);
}