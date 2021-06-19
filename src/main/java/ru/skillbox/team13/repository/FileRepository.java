package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Attachment;

public interface FileRepository extends JpaRepository<Attachment, Integer> {
}
