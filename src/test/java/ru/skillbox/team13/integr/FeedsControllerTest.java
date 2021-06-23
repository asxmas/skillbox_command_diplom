package ru.skillbox.team13.integr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skillbox.team13.DomainObjectFactory;
import ru.skillbox.team13.RequestService;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skillbox.team13.DomainObjectFactory.genString;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedsControllerTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    RequestService requestService;

    String url = "http://localhost:8080/api/v1/feeds";

    int totalPeople = 200;
    int friendsPosts;

    @BeforeAll
    void prepare() {
        Random r = new Random();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person mainPerson = DomainObjectFactory.makePerson(genString(5, 0f, false),
                genString(10, 0f, false), genString(20, 0f, false));
        em.persist(mainPerson);

        User user = DomainObjectFactory.makeUser("main@mail");
        user.setPerson(mainPerson);
        em.persist(user);

        for (int i = 0; i < totalPeople; i++) {
            Person person = DomainObjectFactory.makePerson(genString(5, 0f, false),
                    genString(10, 0f, false), genString(20, 0f, false));
            em.persist(person);

            boolean isFriend = r.nextBoolean();
            if (isFriend) {
                FriendshipStatus friendshipStatus = new FriendshipStatus(LocalDateTime.now(), "", FriendshipStatusCode.FRIEND);
                Friendship friendship = new Friendship(friendshipStatus, mainPerson, person);
                em.persist(friendshipStatus);
                em.persist(friendship);
            }

            while (r.nextBoolean()) {
                Post post = DomainObjectFactory.makePost(genString(40, 0.1f, false), genString(200, 0.2f, true));
                post.setTime(LocalDateTime.now().minus(r.nextInt(1440), ChronoUnit.MINUTES));
                post.setAuthor(person);
                em.persist(post);
                if (isFriend) friendsPosts++;

                while (r.nextBoolean()) {
                    Comment c = DomainObjectFactory.makeComment(genString(100, 0.2f, true));
                    c.setAuthor(person);
                    c.setPost(post);
                    em.persist(c);
                }

                while (r.nextBoolean()) {
                    Like l = DomainObjectFactory.makeLike(person, post);
                    em.persist(l);
                }
            }
        }

        em.getTransaction().commit();
        em.close();
    }


    @Test
    @WithMockUser(username = "main@mail")
    void testFeed() {
        requestService.doRequest(get(url), status().isOk(), true);
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testPagination() {
        List<PostDto> feed = requestService.getAsFeed(get(url), false);
        assertEquals(20, feed.size());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testSize() {
        DTOWrapper wr = requestService.getAsWrapper(get(url), true);
        assertEquals(friendsPosts, wr.getTotal());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testChronologicalOrder() {
        List<PostDto> feed = requestService.getAsFeed(get(url), false);
        long latestPostTimestamp = feed.get(0).getTimestamp();
        long earlierPostTimestamp = feed.get(feed.size() - 1).getTimestamp();
        assertTrue(latestPostTimestamp > earlierPostTimestamp);
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testContainsComments() {
        List<PostDto> feed = requestService.getAsFeed(get(url), false);
        assertTrue(feed.stream().anyMatch(pdto -> pdto.getComments().size() > 0));
    }


    @Test
    @WithMockUser(username = "main@mail")
    void testContainsLikes() {
        List<PostDto> feed = requestService.getAsFeed(get(url), false);
        assertTrue(feed.stream().anyMatch(pdto -> pdto.getLikes() > 0));
    }

}
