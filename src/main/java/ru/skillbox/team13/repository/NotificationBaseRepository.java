package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.NotificationBase;

import java.util.List;

public interface NotificationBaseRepository extends JpaRepository<NotificationBase, Integer> {


}
