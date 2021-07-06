package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Country;

public interface CountryRepository extends CrudRepository<Country,Integer> {
}
