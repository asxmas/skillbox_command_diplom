package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.User;

public interface RepoUser extends CrudRepository<User, Integer> {
}