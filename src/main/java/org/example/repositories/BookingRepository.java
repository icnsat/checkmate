package org.example.repositories;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.Hotel;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
}
