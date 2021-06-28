package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.NotificationBase;

public interface NotificationRepository extends JpaRepository<NotificationBase, Integer> {
}
