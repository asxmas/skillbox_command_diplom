package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
