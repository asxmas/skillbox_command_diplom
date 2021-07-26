package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Subscription;
import ru.skillbox.team13.entity.enums.NotificationCode;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("select s.type from Subscription s where s.person.id = :personId")
    List<NotificationCode> findAllTypesByPersonId(int personId);

    Optional<Subscription> findByTypeEqualsAndPerson(NotificationCode type, Person person);
}
