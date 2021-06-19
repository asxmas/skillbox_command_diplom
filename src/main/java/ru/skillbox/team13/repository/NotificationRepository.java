package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
