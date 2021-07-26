package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.test_util.DomainObjectFactory;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.*;
import ru.skillbox.team13.entity.projection.Liker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LikeRepoTest {

    @Autowired
    LikeRepository likeRepo;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    EntityManagerFactory emf;

    int postId;
    int personId;
    private int commentId;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person = DomainObjectFactory.makePerson("Bob", "Bobson", "@1");

        Post post = DomainObjectFactory.makePost("some title", "some text");
        post.setAuthor(person);

        Comment comment = DomainObjectFactory.makeComment("hello");
        comment.setPost(post);
        comment.setAuthor(person);

        em.persist(person);
        em.persist(post);
        em.persist(comment);

        em.getTransaction().commit();

        postId = post.getId();
        personId = person.getId();
        commentId = comment.getId();

        em.close();
    }

    @AfterAll
    void destroy() {
        commentRepo.deleteAll();
        postRepo.deleteAll();
        userRepository.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    @Transactional
    void testFindLikedPost() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(em.find(Person.class, personId));
        like.setPost(em.find(Post.class, postId));

        em.persist(like);
        em.getTransaction().commit();
        em.close();

        Person p = personRepo.findById(personId).get();
        assertEquals(1, likeRepo.countByLikerAndItemId(p, postId));
    }

    @Test
    @Transactional
    void testFindLikedComment() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Like like = new Like();
        like.setTime(LocalDateTime.now());
        like.setPerson(em.find(Person.class, personId));
        like.setComment(em.find(Comment.class, commentId));

        em.persist(like);
        em.getTransaction().commit();
        em.close();

        Person p = personRepo.findById(personId).get();
        assertEquals(1, likeRepo.countByLikerAndItemId(p, commentId));
    }

    @Test
    @Transactional
    void testFindLikeIds() {
        int total = 5;
        Post likedPost = postRepo.findById(postId).get();

        List<Person> persons = IntStream.range(0, total)
                .mapToObj(i -> DomainObjectFactory.makePerson(i + "@mail", String.valueOf(i), String.valueOf(i)))
                .collect(Collectors.toList());
        personRepo.saveAll(persons);

        List<Like> likes = IntStream.range(0, total).mapToObj(i -> {
            Like like = new Like();
            like.setTime(LocalDateTime.now());
            like.setPerson(persons.get(i));
            like.setPost(likedPost);
            return like;
        }).collect(Collectors.toList());

        likeRepo.saveAll(likes);

        List<Liker> likers = likeRepo.findLikersProjectionsForItemId(postId);

        assertEquals(total, likers.size());
        List<Integer> inputIds = persons.stream().map(Person::getId).collect(Collectors.toList());
        List<Integer> outputIds = likers.stream().map(Liker::getLikerId).collect(Collectors.toList());
        assertTrue(outputIds.containsAll(inputIds));
    }

    @Test
    @Transactional
    void testCountLikes() {

        Person p = personRepo.findById(personId).get();
        assertEquals(0, likeRepo.countByLikerAndItemId(p, postId));
        assertEquals(0, likeRepo.countByLikerAndItemId(p, commentId));

        Like l1 = new Like();
        l1.setPost(postRepo.findById(postId).get());
        l1.setPerson(p);
        l1.setTime(LocalDateTime.now());

        likeRepo.save(l1);

        assertEquals(1, likeRepo.countByLikerAndItemId(p, postId));
    }

    @Test
    @Transactional
    void testLikeDislike() {

        Person p = personRepo.findById(personId).get();
        assertEquals(0, likeRepo.countByLikerAndItemId(p, postId));

        Like l1 = new Like();
        l1.setPost(postRepo.findById(postId).get());
        l1.setPerson(p);
        l1.setTime(LocalDateTime.now());

        likeRepo.save(l1);

        assertEquals(1, likeRepo.countByLikerAndItemId(p, postId));

        likeRepo.deleteByLikerAndId(p, postId);

        assertEquals(0, likeRepo.countByLikerAndItemId(p, postId));
    }
}
