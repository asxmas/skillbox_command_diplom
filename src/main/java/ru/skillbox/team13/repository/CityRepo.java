package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.City;

public interface CityRepo extends JpaRepository<City, Integer> {
}
