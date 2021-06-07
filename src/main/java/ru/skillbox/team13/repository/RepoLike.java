package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Like;

public interface RepoLike extends CrudRepository<Like, Integer> {
}
