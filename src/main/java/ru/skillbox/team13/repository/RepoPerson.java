package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Person;

public interface RepoPerson extends CrudRepository<Person, Integer> {
}
