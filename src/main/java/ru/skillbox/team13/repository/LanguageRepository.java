package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Language;

public interface LanguageRepository extends JpaRepository<Language,Integer> {
}
