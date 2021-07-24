package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Notification;
import ru.skillbox.team13.entity.Subscription;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findAllByPersonId(int personId);

    void deleteAllByPersonId(int personId);

    Notification findNotificationByPersonIdAndId(int personId, int id);

    void deleteByPersonIdAndId(int personId, int id);

}
