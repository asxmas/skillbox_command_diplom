package ru.skillbox.team13.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.repository.TagRepository;
import ru.skillbox.team13.service.impl.TagServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import static ru.skillbox.team13.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagServiceTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void addTagTest() {
        TagDto tagDto1 = new TagDto();
        TagDto savedTagDto1;
        String stringForAddedToSecondTag = genString(20, 0.1f, false);;
        String stringForTagDto1and3 = stringForAddedToSecondTag + genString(20, 0.1f, false);
        tagDto1.setTag(stringForTagDto1and3);
        savedTagDto1 = tagService.addTag(tagDto1);

        assertNotEquals(savedTagDto1.getId(), 0);
        assertEquals(savedTagDto1.getTag(), tagDto1.getTag());
    }

    @Test
    void addDuplicateTagTest(){
        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        TagDto tagDto3 = new TagDto();
        String stringForAddedToSecondTag = genString(20, 0.1f, false);
        String stringForTagDto1and3 = stringForAddedToSecondTag + genString(20, 0.1f, false);;
        tagDto1.setTag(stringForTagDto1and3);
        tagDto2.setTag(stringForAddedToSecondTag);
        tagDto3.setTag(stringForTagDto1and3);
        tagService.addTag(tagDto1);

        int countBeforeAddedDupTag = tagRepository.countTags(stringForTagDto1and3);
        tagService.addTag(tagDto3);
        int countAfterAddedDubTag = tagRepository.countTags(stringForTagDto1and3);

        assertEquals(countBeforeAddedDupTag, countAfterAddedDubTag);
    }

    @Test
    void getTagsTest() throws JsonProcessingException {
        String stringForTag1 = genString(20, 0.1f, false);
        String stringForTag2 = stringForTag1 + genString(20, 0.1f, false);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag tag1 = new Tag();
        tag1.setTag(stringForTag1);
        Tag tag2 = new Tag();
        tag2.setTag(stringForTag2);
        Tag tag3 = new Tag();
        tag3.setTag(genString(30, 0.1f, false));
        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        em.close();

        TagDto[] tags = objectMapper.readValue(objectMapper.
                writeValueAsString(tagService.getTags(stringForTag1.substring(5, 15), 0, 20).getData()), TagDto[].class);

        assertEquals(2, tags.length);
        assertTrue(Arrays.stream(tags).anyMatch(tag -> tag.getTag().equals(stringForTag2)));
    }

    @Test
    void deleteTagTest(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setTag(genString(30, 0.1f, false));
        tag2.setTag(genString(30, 0.1f, false));
        em.persist(tag1);
        em.persist(tag2);
        int tag1id = tagRepository.save(tag1).getId();
        tagRepository.save(tag2);
        em.close();

        int countBeforeDelete = tagRepository.countTags("");
        tagService.deleteTag(tag1id);
        int countAfterDelete = tagRepository.countTags("");

        assertNotEquals(countAfterDelete, countBeforeDelete);
    }
}