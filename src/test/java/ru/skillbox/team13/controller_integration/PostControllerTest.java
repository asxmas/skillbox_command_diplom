package ru.skillbox.team13.controller_integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.skillbox.team13.dto.DTOWrapper;
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
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skillbox.team13.test_util.DomainObjectFactory.genString;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ObjectMapper om;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RequestService requestService;

    Person mainPerson;

    String url = "http://localhost:8080/api/v1/post";

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        mainPerson = DomainObjectFactory.makePerson();
        em.persist(mainPerson);

        User user = DomainObjectFactory.makeUser("posts@mail", mainPerson);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void testSimpleFind() {
        createAndPersistPost("substring", "substring");
        DTOWrapper w = requestService.getAsWrapper(get(url).param("text", "substring"), false);
        assertEquals(1, w.getTotal());
    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void findByID() {
        int id = createAndPersistPost();
        PostDto dto = requestService.getAsPostDto(get(url + "/" + id), false);

        assertEquals(200, dto.getText().length());
//        assertEquals(20, dto.getAuthor().getEmail().length());
    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void findDeletedByID() {
        Post post = DomainObjectFactory.makePost(mainPerson);
        post.setDeleted(true);
        int id = createAndPersistPost(post, new String[0]);
        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true);
    }

    @Test
    @WithMockUser(username = "posts@mail")
    void testWrongId() {
        requestService.doRequest(get(url + "/100500"), status().isBadRequest(), true);
    }

    @Test
//    @Transactional
    @WithMockUser(username = "posts@mail")
    void testEdit() throws JsonProcessingException {
        int id = createAndPersistPost(new String[]{"tag1", "tag2", "tag3"});
        AddPostDto payload = new AddPostDto("rootin", "tootin", new String[] {"tag3", "tag4"});
        MessageDTO message = requestService.getAsMessageDTO(put(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertTrue(message.getMessage().contains("rootin"));

        String postUrl = "http://localhost:8080/api/v1/post/" + id;
        PostDto dto = requestService.getAsPostDto(get(postUrl), true);

        assertEquals("rootin", dto.getTitle());
        assertEquals("tootin", dto.getText());
        assertEquals(2, dto.getTags().size());
        assertTrue(dto.getTags().contains("tag3"));
        assertTrue(dto.getTags().contains("tag4"));


    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void testEditNonExisting() throws JsonProcessingException {
//        Map<String, String> payload = Map.of("k1", "v1", "k2", "v2");
        AddPostDto payload = new AddPostDto("k1", "v1", new String[0]);

        requestService.doRequest(put(url + "/" + 100500)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(payload)),
                status().isBadRequest(), true);
    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void testEditWithTime() throws JsonProcessingException {
        int id = createAndPersistPost("", "");

        Long time = TimeUtil.getTimestamp(LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0));
//        Map<String, String> payload = Map.of("title", "cowboy", "post_text", "shootin");
        AddPostDto payload = new AddPostDto("cowboy", "shootin", new String[0]);

        MessageDTO message = requestService.getAsMessageDTO(put(url + "/" + id)
                .param("publish_date", String.valueOf(time))
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertTrue(message.getMessage().contains("shootin"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void testDelete() {
        int id = createAndPersistPost("", "");

        requestService.doRequest(delete(url + "/" + id), status().isOk(), true);

        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true);
    }


    @Test
    @Transactional
    @WithMockUser(username = "posts@mail")
    void testDeleteAndRecover() {
        int id = createAndPersistPost("", "");

        requestService.doRequest(get(url + "/" + id), status().isOk(), true); //exists
        requestService.doRequest(delete(url + "/" + id), status().isOk(), true); //deletes
        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true); //no data

        requestService.doRequest(put(url + "/" + id + "/recover"), status().isOk(), true); //recover

        requestService.doRequest(get(url + "/" + id), status().isOk(), true); //exists
    }

    int createAndPersistPost(Post post, String[] tags) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (String tag : tags) {
            Tag t = new Tag(tag);
            em.persist(t);
            post.getTags().add(t);
        }
        em.persist(post);

        int id = post.getId();

        em.getTransaction().commit();
        em.close();
        return id;
    }

    int createAndPersistPost(String title, String text, String[] tags) {
        return createAndPersistPost(DomainObjectFactory.makePost(title, text, mainPerson), tags);
    }

    int createAndPersistPost(String title, String text) {
        return createAndPersistPost(title, text, new String[0]);
    }

    private int createAndPersistPost(String[] tags) {
        return createAndPersistPost(genString(20), genString(200), tags);
    }

    int createAndPersistPost() {
        return createAndPersistPost(new String[0]);
    }
}
