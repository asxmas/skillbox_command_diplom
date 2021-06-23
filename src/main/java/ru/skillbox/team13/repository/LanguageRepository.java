package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Languages;

public interface LanguageRepository extends CrudRepository<Languages,Integer>{
}
