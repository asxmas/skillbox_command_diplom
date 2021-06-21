package ru.skillbox.team13.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.service.impl.FeedsServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.skillbox.team13.DomainObjectFactory.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class FeedsServiceTest {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    FeedsServiceImpl feedsService;

    List<Post> postList;
    Person p;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        p = makePerson(genString(5, 0f, false), genString(5, 0f, false), genString(10, 0f, false));
        em.persist(p);

        User u = makeUser("email");
        u.setPerson(p);
        em.persist(u);


        postList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Post post = makePost(genString(20, 0.1f, false), genString(200, 0.2f, true));
            post.setAuthor(p);
            em.persist(post);
            postList.add(post);
        }

        for (Post px : postList) {
            int commentCount = new Random().nextInt(10) + 1;
            for (int i = 0; i < commentCount; i++) {
                Comment c = makeComment(genString(50, 0.15f, true));
                c.setPost(px);
                c.setAuthor(p);
                em.persist(c);
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void testGetFeed() {
        assertNotNull(feedsService);
    }


    @Test
    @WithMockUser(username = "email")
    void testComments() {
        List<Post> postsWithAuthors = feedsService.getPosts(List.of(p), PageRequest.of(0, 10));
        assertNotNull(postsWithAuthors.get(0).getAuthor());

        List<CommentProjection> comments = feedsService.fetchComments(postsWithAuthors.get(5));
        assertTrue(comments.size() > 0);
    }
}
