package org.example.repositories;

import org.example.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);

    @Query("SELECT b FROM Booking b WHERE b.room.hotel IN :hotels")
    List<Booking> findByHotelIn(List<Hotel> hotels);

    void deleteAllByRoom(Room room);

    List<Booking> findByRoom(Room room);

    Booking findBookingById(Long bookingId);
}
