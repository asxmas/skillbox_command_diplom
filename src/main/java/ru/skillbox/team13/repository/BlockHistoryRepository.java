package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Block;

public interface BlockHistoryRepository extends JpaRepository<Block, Integer> {
}
