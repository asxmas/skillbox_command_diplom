package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Tag;

public interface RepoTag extends CrudRepository<Tag, Integer> {
}

