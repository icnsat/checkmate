package org.example.repositories;

import org.example.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @brief Репозиторий для работы с сущностями BookingStatus (Статус бронирования).
 * @details Обеспечивает доступ к данным статусов бронирования.
 */
@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {

    /**
     * @brief Найти статус бронирования по его имени.
     * @param name Имя статуса (например, "PENDING", "CONFIRMED", "CANCELLED").
     * @return Объект BookingStatus, соответствующий имени, или null, если не найден.
     */
    BookingStatus findBookingStatusByName(String name);
}