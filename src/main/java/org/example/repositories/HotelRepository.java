package org.example.repositories;

import org.example.entities.City;
import org.example.entities.Hotel;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @brief Репозиторий для управления сущностями Hotel (Отели).
 * @details Содержит методы поиска отелей по различным критериям, включая город,
 * доступность номеров и управляющего пользователя.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * @brief Найти все отели, расположенные в заданном городе.
     * @param city Объект City, по которому производится фильтрация.
     * @return Список отелей, находящихся в указанном городе.
     */
    List<Hotel> findByCity(City city);

    /**
     * @brief Найти отели с доступными номерами на заданные даты и по заданному количеству взрослых.
     * @details Отели подбираются с учетом:
     * - города,
     * - вместимости номеров,
     * - отсутствия пересечения дат с уже существующими бронями.
     *
     * @param cityId Идентификатор города.
     * @param checkInDate Дата заезда.
     * @param checkOutDate Дата выезда.
     * @param adults Количество взрослых.
     * @return Список отелей, удовлетворяющих условиям.
     */
    @Query("""
           SELECT DISTINCT h FROM Hotel h
           JOIN h.rooms r
           LEFT JOIN Booking b ON b.room.id = r.id
           WHERE h.city.id = :cityId
           AND r.capacity >= :adults
           AND (b.id IS NULL OR (:checkOutDate <= b.checkInDate OR :checkInDate >= b.checkOutDate))
           """)
    List<Hotel> findHotelsWithAvailableRooms(
            @Param("cityId") Long cityId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("adults") int adults
    );

    /**
     * @brief Найти все отели, которыми управляет указанный пользователь.
     * @param currentUser Пользователь-менеджер.
     * @return Список отелей, закреплённых за пользователем.
     */
    List<Hotel> findAllByManager(User currentUser);
}