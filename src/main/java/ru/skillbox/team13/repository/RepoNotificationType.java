package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.NotificationType;

public interface RepoNotificationType extends CrudRepository<NotificationType, Integer> {
}
