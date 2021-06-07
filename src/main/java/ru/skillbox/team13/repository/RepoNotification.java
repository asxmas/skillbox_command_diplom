package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Notification;

public interface RepoNotification extends CrudRepository<Notification, Integer> {
}
