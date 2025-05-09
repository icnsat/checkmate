package org.example.repositories;

import org.example.entities.BookingStatus;
import org.example.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAll();

    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<City> findByNameContainingIgnoreCase(@Param("query") String query);

    City findCityById(Long id);

    Optional<City> findByName(String trim);

    City findCityByName(String trim);

}
