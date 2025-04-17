package org.example.services;

import org.example.entities.City;
import org.example.repositories.BookingRepository;
import org.example.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @brief Сервис для работы с городами.
 * @details Предоставляет методы для получения, поиска и создания городов.
 */
@Service
public class CityService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private CityRepository cityRepository;

    /**
     * @brief Конструктор сервиса.
     * @param cityRepository Репозиторий для работы с сущностями City.
     */
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * @brief Получить город по его ID.
     * @param id Уникальный идентификатор города.
     * @return Объект City, если найден.
     */
    public City getCityById(Long id){
        return cityRepository.findCityById(id);
    }

    /**
     * @brief Получить список всех городов.
     * @return Список всех городов в базе данных.
     */
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    /**
     * @brief Поиск городов по названию (без учёта регистра).
     * @param query Строка поиска (частичное или полное имя города).
     * @return Список городов, подходящих под условие поиска.
     */
    public List<City> searchCities(String query) {
        return cityRepository.findByNameContainingIgnoreCase(query);
    }

    /**
     * @brief Найти город по имени и стране или создать новый.
     * Ищет город с заданными параметрами. Если такой город не найден, создаёт и сохраняет новый.
     *
     * @param city Название города.
     * @param country Название страны.
     * @return Существующий или вновь созданный объект City.
     */
    public City findOrCreateByName(String city, String country) {

//        // Ищем город в базе данных
//        Optional<City> cityOptional = cityRepository.findByName(city.trim());
//
//        // Если город найден, возвращаем его
//        if (cityOptional.isPresent()) {
//            return cityOptional.get();
//        }

        City cityFound = cityRepository.findCityByName(city.trim());
        if (cityFound != null && Objects.equals(cityFound.getCountry(), country)) {
            return cityFound;
        }

        // Если город не найден, создаём новый
        City newCity = new City();
        newCity.setName(city.trim());
        newCity.setCountry(country.trim());
        return cityRepository.save(newCity); // Сохраняем в базу и возвращаем
    }
}