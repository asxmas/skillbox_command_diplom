package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.City;

public interface CitiesRepository extends CrudRepository <City,Integer> {

}
