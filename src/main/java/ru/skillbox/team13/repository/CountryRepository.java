package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Country;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    Optional<Country> findByTitle(String title);
}
