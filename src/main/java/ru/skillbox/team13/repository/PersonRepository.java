package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Person;

import java.util.ArrayList;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select count(p) from Person p where lower(p.firstName)  like :name or lower(p.lastName) like :name")
    int countPersons(String name);

    @Query("select p from Person p where lower(p.firstName) like :name or lower(p.lastName) like :name")
    List<Person> findFriendsByName(Pageable p, String name);

    List<Person> findByCity(Pageable p, City city);

    Integer countByCity(City city);
}
