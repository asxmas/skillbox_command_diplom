package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Storage;


public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage findByFileName(String fileName);
    Storage findByOwnerId(int ownerId);
    Storage findById(int id);
}
