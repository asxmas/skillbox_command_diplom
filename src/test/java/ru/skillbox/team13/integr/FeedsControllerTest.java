package ru.skillbox.team13.integr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skillbox.team13.DomainObjectFactory;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.repository.FriendshipRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.skillbox.team13.DomainObjectFactory.genString;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FriendshipRepository frRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ObjectMapper om;

    @Autowired
    EntityManagerFactory emf;

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

        for (int i = 0; i < 5; i++) {
            Person person = DomainObjectFactory.makePerson(genString(5, 0f, false),
                    genString(10, 0f, false), genString(20, 0f, false));
            em.persist(person);

            FriendshipStatus friendshipStatus = new FriendshipStatus(LocalDateTime.now(), "", FriendshipStatusCode.FRIEND);
            Friendship friendship = new Friendship(friendshipStatus, mainPerson, person);
            em.persist(friendshipStatus);
            em.persist(friendship);

            Post post = DomainObjectFactory.makePost(genString(40, 0.1f, false), genString(200, 0.2f, true));
            post.setAuthor(person);
            em.persist(post);
        }

        em.getTransaction().commit();
        em.close();
    }


    @Test
    @WithMockUser(username = "main@mail")
    void testFeed() {
        assertNotNull(performRequest(get("http://localhost:8080/api/v1/feeds")));
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testFeedContents() throws JsonProcessingException {
        DTOWrapper wr = performRequest(get("http://localhost:8080/api/v1/feeds"));
        String data = om.writeValueAsString(wr.getData());
        PostDto[] feed = om.readValue(data, PostDto[].class);
        assertEquals(5, feed.length);
    }

    @SneakyThrows
    DTOWrapper performRequest(RequestBuilder req) {
        String json = mockMvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return om.readValue(json, DTOWrapper.class);
    }

}
