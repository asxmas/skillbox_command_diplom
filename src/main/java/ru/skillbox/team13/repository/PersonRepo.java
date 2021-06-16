package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Person;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Integer> {

    @Query("select count(p) from Person p where lower(p.firstName)  like :q or lower(p.lastName) like :q")
    int countPersons(@Param("q") String name);

    @Query("select p from Person p where lower(p.firstName) like :s or lower(p.lastName) like :s")
    List<Person> findFriendsByName(Pageable p, @Param("s") String name);

    List<Person> findByCity(Pageable p, City city);

    Integer countByCity(City city);
}
