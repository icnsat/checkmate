package org.example.repositories;

import org.example.entities.Hotel;
import org.example.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
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

    Room findRoomById(Long roomId);

    @Query("SELECT r.hotel FROM Room r WHERE r.id = :roomId")
    Hotel findHotelByRoomId(@Param("roomId") Long roomId);

}
