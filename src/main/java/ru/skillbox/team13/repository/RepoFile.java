package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.File;

public interface RepoFile extends CrudRepository<File, Integer> {
}
