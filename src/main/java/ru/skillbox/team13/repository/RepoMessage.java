package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Message;

public interface RepoMessage extends CrudRepository<Message, Integer> {
}
