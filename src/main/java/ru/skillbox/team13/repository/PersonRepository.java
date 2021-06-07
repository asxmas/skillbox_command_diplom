package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
