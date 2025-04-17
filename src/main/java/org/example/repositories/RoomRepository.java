package org.example.repositories;

import org.example.entities.Hotel;
import org.example.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @brief Репозиторий для управления сущностями Room (Комнаты).
 * @details Содержит методы для работы с комнатами, включая поиск доступных комнат, поиск комнаты по ID и извлечение отеля по ID комнаты.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * @brief Найти доступные комнаты в указанном отеле на заданные даты.
     * @param hotelId Идентификатор отеля.
     * @param checkInDate Дата заезда.
     * @param checkOutDate Дата выезда.
     * @param adults Количество взрослых.
     * @return Список доступных комнат в указанном отеле на указанные даты.
     */
    @Query("""
           SELECT r FROM Room r
           LEFT JOIN Booking b ON b.room.id = r.id
           WHERE r.hotel.id = :hotelId
           AND r.capacity >= :adults
           AND (b.id IS NULL OR (:checkOutDate <= b.checkInDate OR :checkInDate >= b.checkOutDate))
           """)
    List<Room> findAvailableRooms(
            @Param("hotelId") Long hotelId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("adults") int adults
    );

    /**
     * @brief Найти комнату по ID.
     * @param roomId Идентификатор комнаты.
     * @return Комната с указанным ID.
     */
    Room findRoomById(Long roomId);

    /**
     * @brief Найти отель, к которому принадлежит комната по её ID.
     * @param roomId Идентификатор комнаты.
     * @return Отель, в котором находится комната с указанным ID.
     */
    @Query("SELECT r.hotel FROM Room r WHERE r.id = :roomId")
    Hotel findHotelByRoomId(@Param("roomId") Long roomId);

    /**
     * @brief Найти все комнаты в указанном отеле.
     * @param hotel Отель, для которого нужно найти комнаты.
     * @return Список всех комнат, принадлежащих указанному отелю.
     */
    List<Room> findAllByHotel(Hotel hotel);
}