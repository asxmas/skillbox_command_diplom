package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.repository.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManagerFactory emf;

    int firstTagId;
    int secondTagId;
    int thirdTagId;

    @BeforeAll
    @Test
    void saveTestTags() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag firstTag = new Tag();
        firstTag.setTag("Tag is worked");
        Tag secondTag = new Tag();
        secondTag.setTag("Tag is worked correctly");
        Tag thirdTag = new Tag();
        thirdTag.setTag("Cat is exploded");

        em.persist(firstTag);
        em.persist(secondTag);
        em.persist(thirdTag);

        firstTagId = tagRepository.save(firstTag).getId();
        secondTagId = tagRepository.save(secondTag).getId();
        thirdTagId = tagRepository.save(thirdTag).getId();

        em.close();
    }

    @AfterAll
    void destroy() {
        tagRepository.deleteAll();
    }

    @Test
    void testFindByName() {
        List<Tag> tags = tagRepository.findTagsByTag((PageRequest.of(0, 10)), "%is worked%");
        assertEquals("Tag is worked", tags.get(0).getTag());
    }

    @Test
    void testCountByName() {
        int countTags = tagRepository.countTags("%is worked");
        assertEquals(2, countTags);
    }

    @Test
    void testCreateDuplicateTag() {
        boolean throwned = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag originalTag = new Tag();
        Tag duplicateTag = new Tag();
        originalTag.setTag("It is original");
        em.persist(originalTag);
        tagRepository.save(originalTag);

        duplicateTag.setTag("It is original");
        em.persist(duplicateTag);
        em.close();

        try {
            tagRepository.save(duplicateTag);
            tagRepository.countTags("%is original");
        } catch (DataIntegrityViolationException ex) {
            throwned = true;
        }
        //todo      assertThrows()
        assertTrue(throwned);
    }
}
