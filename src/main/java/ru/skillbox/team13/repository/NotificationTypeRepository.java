package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.NotificationType;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {
}
