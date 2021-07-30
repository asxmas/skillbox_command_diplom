package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Country;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}
