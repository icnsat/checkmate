package org.example.repositories;

import org.example.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

/**
 * @brief Репозиторий для работы с сущностями Booking (Бронирование).
 * @details Предоставляет методы доступа к данным бронирований, в том числе по пользователям, отелям и комнатам.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * @brief Найти все бронирования для заданного пользователя.
     * @param user Пользователь, чьи бронирования нужно получить.
     * @return Список бронирований пользователя.
     */
    List<Booking> findAllByUser(User user);

    /**
     * @brief Найти все бронирования, связанные с отелями из списка.
     * @param hotels Список отелей.
     * @return Список бронирований, связанных с этими отелями.
     */
    @Query("SELECT b FROM Booking b WHERE b.room.hotel IN :hotels")
    List<Booking> findByHotelIn(List<Hotel> hotels);

    /**
     * @brief Удалить все бронирования, связанные с указанной комнатой.
     * @param room Комната, бронирования которой необходимо удалить.
     */
    void deleteAllByRoom(Room room);

    /**
     * @brief Найти все бронирования по конкретной комнате.
     * @param room Комната, по которой осуществляется поиск.
     * @return Список бронирований по комнате.
     */
    List<Booking> findByRoom(Room room);

    /**
     * @brief Найти бронирование по его идентификатору.
     * @param bookingId Идентификатор бронирования.
     * @return Объект Booking, если найден.
     */
    Booking findBookingById(Long bookingId);
}