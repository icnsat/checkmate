package org.example.services;

import org.example.entities.Hotel;
import org.example.entities.Room;
import org.example.repositories.HotelRepository;
import org.example.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @brief Сервис для управления номерами в отелях.
 * @details Обеспечивает операции по получению, сохранению и удалению номеров, а также
 * предоставляет методы для поиска номеров по отелю.
 */
@Service
public class RoomService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RoomRepository roomRepository;

    /**
     * @brief Конструктор сервиса RoomService.
     * @param roomRepository Репозиторий номеров.
     */
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * @brief Получение номера по его идентификатору.
     * @param roomId Идентификатор номера.
     * @return Объект Room, соответствующий заданному ID.
     */
    public Room getRoomById(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    /**
     * @brief Получение всех номеров, относящихся к конкретному отелю.
     * @param hotel Отель, к которому относятся номера.
     * @return Список номеров, связанных с данным отелем.
     */
    public List<Room> getRoomsByHotel(Hotel hotel) {
        return roomRepository.findAllByHotel(hotel);
    }

    /**
     * @brief Сохранение или обновление номера.
     * @param room Объект Room для сохранения в базу данных.
     */
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    /**
     * @brief Удаление номера по его ID.
     * @param roomId Идентификатор номера для удаления.
     */
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    /**
     * @brief Поиск всех номеров, относящихся к заданному отелю.
     * @param hotel Отель, по которому осуществляется поиск.
     * @return Список всех номеров, принадлежащих отелю.
     */
    public List<Room> findByHotel(Hotel hotel) {
        return roomRepository.findAllByHotel(hotel);

    }

    /**
     * @brief Удаление всех номеров, принадлежащих отелю.
     * @param hotel Отель, номера которого будут удалены.
     */
    public void deleteByHotel(Hotel hotel) {
        List<Room> rooms = roomRepository.findAllByHotel(hotel);

        if (rooms != null && !rooms.isEmpty()) {
            roomRepository.deleteAll(rooms);
        }
    }

}