package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Integer> {
    Optional<City> findByTitle(String title);
}
