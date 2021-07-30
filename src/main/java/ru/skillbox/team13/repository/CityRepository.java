package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.City;

public interface CityRepository extends JpaRepository<City,Integer> {

}
