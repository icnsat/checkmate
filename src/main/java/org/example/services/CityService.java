package org.example.services;

import org.example.entities.City;
import org.example.repositories.BookingRepository;
import org.example.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CityService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityById(Long id){
        return cityRepository.findCityById(id);
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public List<City> searchCities(String query) {
        return cityRepository.findByNameContainingIgnoreCase(query);
    }

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
