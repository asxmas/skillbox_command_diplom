package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("select count (t) from Tag t where t.tag like %:name%")
    int countTags(String name);

    @Query("select (t) from Tag t where t.tag like %:name%")
    List<Tag> findTagsByTag(Pageable p, String name);

    Tag findTagByTag(String tag);

    boolean existsByTag(String tag);

    Set<Tag> findAllByTagIn(List<String> tagNames);
}


