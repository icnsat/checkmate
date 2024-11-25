package org.example.services;

import org.example.entities.City;
import org.example.repositories.BookingRepository;
import org.example.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
