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

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCity(City city);
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

    List<Hotel> findAllByManager(User currentUser);
}
