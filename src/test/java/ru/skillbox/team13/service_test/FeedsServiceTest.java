package ru.skillbox.team13.service_test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.service.CommentService;
import ru.skillbox.team13.service.impl.PostServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skillbox.team13.test_util.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedsServiceTest {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentService commentService;

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

        em.persist(makePost("SuBsTrInG", "----", p));
        em.persist(makePost("00", "00substring", p));
        em.persist(makePost("test", "substringZZ", p));
        em.persist(makePost("____", "   subString   ", p));


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
        assertNotNull(postService);
    }


    @Test
    @WithMockUser(username = "email")
    void testComments() {
        List<Post> postsWithAuthors = postService.getPosts(List.of(p), PageRequest.of(0, 10));
        assertNotNull(postsWithAuthors.get(0).getAuthor());

        List<CommentProjection> comments = commentService.getCommentProjections(postsWithAuthors);
        assertTrue(comments.size() > 0);
    }

    @Test
    @WithMockUser(username = "email")
    void testSearchSubstring() {
        List<Post> posts = postService.getPosts(List.of(p), PageRequest.of(0, 10), "subsTrinG");
        assertEquals(4, posts.size());
    }
}
