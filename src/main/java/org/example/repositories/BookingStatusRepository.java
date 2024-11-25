package org.example.repositories;

import org.example.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {

    BookingStatus findBookingStatusByName(String name);
}
