package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Block;

public interface RepoBlockHistory extends CrudRepository<Block, Integer> {
}
