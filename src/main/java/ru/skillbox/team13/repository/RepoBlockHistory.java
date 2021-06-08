package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.BlockHistory;

public interface RepoBlockHistory extends CrudRepository<BlockHistory, Integer> {
}
