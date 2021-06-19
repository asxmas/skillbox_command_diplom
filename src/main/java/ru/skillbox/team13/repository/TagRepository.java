package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}

