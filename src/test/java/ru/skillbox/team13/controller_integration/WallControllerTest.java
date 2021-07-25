package ru.skillbox.team13.controller_integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.AddPostDto;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.test_util.DomainObjectFactory;
import ru.skillbox.team13.test_util.RequestService;
import ru.skillbox.team13.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WallControllerTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    RequestService requestService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper om;

    String url;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person author = DomainObjectFactory.makePerson("author@email");
        em.persist(author);

        User user = DomainObjectFactory.makeUser("author@email", author);
        em.persist(user);

        Tag t1 = new Tag("test1");
        Tag t2 = new Tag("test2");
        Tag t3 = new Tag("test3");
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        url = "http://localhost:8080/api/v1/users/" + author.getId() + "/wall";
        Post post1 = DomainObjectFactory.makePost(author);
        Post post2 = DomainObjectFactory.makePost(author);
        Post post3 = DomainObjectFactory.makePost(author);

        post1.setTags(Set.of(t1));
        post2.setTags(Set.of(t2, t3));

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    void destroy() {
        postRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "author@email")
    void testConnectWall() {
        requestService.doRequest(get(url), status().isOk(), true);
    }


    @Test
    @WithMockUser(username = "author@email")
    void testGetWall() {
        List<PostDto> posts = requestService.getAsPostsDtoList(get(url), true);
        assertEquals(1, posts.get(2).getTags().size());
        assertEquals(2, posts.get(1).getTags().size());
        assertEquals(0, posts.get(0).getTags().size());
        assertEquals(3, posts.size());
    }

    @Test
    @Transactional
    @WithMockUser(username = "author@email")
    void testPost() throws JsonProcessingException {
        AddPostDto post = new AddPostDto("Rootin' tootin'", "Cowboy shootin'",
                new String[]{"test tag 1", "test tag 2"});
        String json = om.writeValueAsString(post);
        MessageDTO message = requestService.getAsMessageDTO(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(json), false);

        int id;
        try {
            id = Integer.parseInt(message.getMessage().substring(6, 8));
        } catch (NumberFormatException e) {
            id = Integer.parseInt(message.getMessage().substring(6, 7));
        }

        String postUrl = "http://localhost:8080/api/v1/post/" + id;
        PostDto dto = requestService.getAsPostDto(get(postUrl), true);

        assertEquals("Rootin' tootin'", dto.getTitle());
        assertEquals("Cowboy shootin'", dto.getText());
        assertTrue(dto.getTags().contains("test tag 1"));
        assertTrue(dto.getTags().contains("test tag 2"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "author@email")
    void testDelayedPost() throws JsonProcessingException {
        AddPostDto post = new AddPostDto("bang bang", "you're dead", new String[1]);
        String json = om.writeValueAsString(post);
        LocalDateTime date = LocalDateTime.of(2035, Month.DECEMBER, 31, 23, 55);
        long timestamp = TimeUtil.getTimestamp(date);
        MessageDTO message = requestService.getAsMessageDTO(post(url)
                .param("publish_date", String.valueOf(timestamp))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json), true);
        assertTrue(message.getMessage().contains("bang bang"));
    }
}
