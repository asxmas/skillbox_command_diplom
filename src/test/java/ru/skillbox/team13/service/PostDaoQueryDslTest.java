package ru.skillbox.team13.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.LikeRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.skillbox.team13.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostDaoQueryDslTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManagerFactory emf;


    @Autowired
    PostDAO postDAO;

    @BeforeAll
    void prepare() {
        Random r = new Random();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person p = makePerson(genString(5), genString(10), genString(15));
        em.persist(p);

        for (int i = 0; i < 15; i++) {
            Post post = makePost(genString(5) + "substring" + genString(5), genString(200), p);
            setTime(post, i);
            em.persist(post);
        }

        for (int i = 0; i < 15; i++) {
            Post post = makePost(genString(10), genString(100) + "substring" + genString(100), p);
            setTime(post, i);
            em.persist(post);
        }

        for (int i = 0; i < 30; i++) {
            Post post = makePost(genString(20), genString(200), p);
            setTime(post, i);
            em.persist(post);
        }

        em.getTransaction().commit();
        em.close();
    }

    private void setTime(Post post, int i) {
        if (i % 3 == 0) {
            post.setTime(LocalDateTime.now().minusYears(1));
        } else if (i % 3 == 1) {
            post.setTime(LocalDateTime.now().plusYears(1));
        }
    }

    @AfterAll
    void destroy() {
        postRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void testTextSearchOnly() {
        Page<Post> posts = postDAO.findByTextAndTime("substring", null, null, PageRequest.of(0, 100));
        assertEquals(30, posts.getTotalElements());
    }

    @Test
    void testWithOneTime() {
        Page<Post> posts = postDAO.findByTextAndTime("substring", LocalDateTime.now().minusMinutes(1), null, PageRequest.of(0, 100));
        assertEquals(20, posts.getTotalElements());
    }

    @Test
    void testWithTwoTimes() {
        Page<Post> posts = postDAO.findByTextAndTime("substring", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1), PageRequest.of(0, 100));
        assertEquals(10, posts.getTotalElements());
    }
}
