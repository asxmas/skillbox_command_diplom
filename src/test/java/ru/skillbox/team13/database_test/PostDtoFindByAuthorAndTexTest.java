package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skillbox.team13.test_util.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostDtoFindByAuthorAndTexTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    PostDAO postDAO;

    List<Integer> authorsIds;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Person> persons = List.of(makePerson(), makePerson(), makePerson());

        persons.forEach(em::persist);

        authorsIds = persons.stream().map(Person::getId).collect(Collectors.toList());

        List<Post> posts = List.of(
                makePost(persons.get(0)),
                makePost(persons.get(1)), makePost(persons.get(1)),
                makePost(persons.get(2)), makePost(persons.get(2)), makePost(persons.get(2)));

        posts.get(1).setTitle("sUbStRiNg");
        posts.get(2).setTitle("SUBSTRING_____");
        posts.get(3).setPostText("000substring");
        posts.get(3).setTitle("SUBSTRING");

        posts.forEach(em::persist);

        List<Like> likes = makeLike(persons.get(0), posts.get(5), 10);
        likes.forEach(em::persist);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    void fetchNullSubstr() {
        Page<PostDto> page = postDAO.getPostsDtos(authorsIds, null, PageRequest.of(0, 100));
        assertEquals(6, page.getTotalElements());
        assertEquals(0, page.getContent().get(5).getLikes());
        assertEquals(10, page.getContent().get(0).getLikes());
        assertNotNull(page.getContent().get(0).getAuthor());
        assertTrue(page.getContent().get(0).getAuthor().getId() != 0);
        assertNull(page.getContent().get(0).getComments());
    }

    @Test
    void fetchEmptySubstr() {
        Page<PostDto> page = postDAO.getPostsDtos(authorsIds, "", PageRequest.of(1, 2));
        assertEquals(6, page.getTotalElements());
        assertEquals(2, page.getContent().size());
    }

    @Test
    void fetchWithSubstr() {
        Page<PostDto> page = postDAO.getPostsDtos(authorsIds, "substring", PageRequest.of(0, 100));
        assertEquals(3, page.getTotalElements());
    }

    @Test
    void countLikes() {
        Page<PostDto> page = postDAO.getPostsDtos(authorsIds, null, PageRequest.of(0, 100));
        PostDto dto = page.getContent().stream().max(Comparator.comparing(PostDto::getLikes)).get();
        assertEquals(10, dto.getLikes());
    }
}
