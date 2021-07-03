package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.team13.test_util.DomainObjectFactory;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.repository.LikeRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.skillbox.team13.test_util.DomainObjectFactory.genString;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManagerFactory emf;

    List<Person> persons;

    List<Post> posts = new ArrayList<>();

    @BeforeAll
    void prepare() {
        Random r = new Random();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person1 = DomainObjectFactory.makePerson("Ben", "Benson", "b@mail");
        em.persist(person1);

        Person person2 = DomainObjectFactory.makePerson("Ron", "Ronson", "r@mail");
        em.persist(person2);

        persons = List.of(person1, person2);

        for (int i = 0; i < 100; i++) {
            Post post = DomainObjectFactory.makePost(genString(15, 0.2f, false),
                    genString(255, 0.15f, true));
            post.setAuthor(persons.get(r.nextInt(1)));
            em.persist(post);
            posts.add(post);
        }

        em.getTransaction().commit();

        em.getTransaction().begin();

        for (int j = 0; j < posts.size(); j++) {
            Post post = posts.get(j);
            for (int i = 0; i < j; i++) {
                Like like = DomainObjectFactory.makeLike(person1, post);
                em.persist(like);
            }
        }

        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    void destroy() {
        postRepository.deleteAll();
        personRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @Test
    void testCountUsersPosts() {
        int count = postRepository.countAllByAuthorIn(persons);
        assertEquals(100, count);
    }

    @Test
    void testGetPosts() {
        List<Post> posts = postRepository.findPostsAndAuthorsFromAuthors(PageRequest.of(0, 10), persons);
        assertEquals(10, posts.size());
    }


    @Test
    void countLikesByPosts() {
        List<Integer> counts = postRepository.countLikesByPosts(posts).stream().map(LikeCount::getLikeCount).collect(Collectors.toList());
        assertEquals(0, counts.get(0));
        assertEquals(50, counts.get(50));
        assertEquals(99, counts.get(99));
    }
}
