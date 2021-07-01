package ru.skillbox.team13.integr;

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
import ru.skillbox.team13.DomainObjectFactory;
import ru.skillbox.team13.RequestService;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.util.TimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skillbox.team13.DomainObjectFactory.genString;

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
        mainPerson = DomainObjectFactory.makePerson(genString(5), genString(10), genString(20));
        em.persist(mainPerson);

        User user = DomainObjectFactory.makeUser("main@mail");
        user.setPerson(mainPerson);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Test
//    @Transactional
    @WithMockUser(username = "main@mail")
    void testSimpleFind() {
        createAndPersistPost("substring", "substring");
        DTOWrapper w = requestService.getAsWrapper(get(url).param("text", "substring"), false);
        assertEquals(1, w.getTotal());
    }

    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void findByID() {
        int id = createAndPersistPost("", "");
        PostDto dto = requestService.getAsPostDto(get(url + "/" + id), false);

        assertEquals(200, dto.getText().length());
        assertEquals(20, dto.getAuthor().getEmail().length());
    }

    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void findDeletedByID() {
        int id = createAndPersistPost("", "");
        Post p = postRepository.getById(id);
        p.setDeleted(true);
        postRepository.save(p);

        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true);
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testWrongId() {
        requestService.doRequest(get(url + "/100500"), status().isBadRequest(), true);
    }

    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void testEdit() throws JsonProcessingException {
        int id = createAndPersistPost("", "");

        Map<String, String> payload = Map.of("title", "rootin", "post_text", "tootin");
        PostDto dto = requestService.getAsPostDto(put(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertEquals("rootin", dto.getTitle());
        assertEquals("tootin", dto.getText());
        assertTrue(dto.getTimestamp() - TimeUtil.getTimestamp(LocalDateTime.now()) < 1000);
    }

    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void testEditWithTime() throws JsonProcessingException {
        int id = createAndPersistPost("", "");

        Long time = TimeUtil.getTimestamp(LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0));
        Map<String, String> payload = Map.of("title", "cowboy", "post_text", "shootin");

        PostDto dto = requestService.getAsPostDto(put(url + "/" + id)
                .param("publish_date", String.valueOf(time))
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertEquals("cowboy", dto.getTitle());
        assertEquals("shootin", dto.getText());
        assertEquals(2000, TimeUtil.getTime(dto.getTimestamp()).getYear());
    }

    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void testDelete() {
        int id = createAndPersistPost("", "");

        requestService.doRequest(delete(url + "/" + id), status().isOk(), true);

        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true);
    }


    @Test
    @Transactional
    @WithMockUser(username = "main@mail")
    void testDeleteAndRecover() {
        int id = createAndPersistPost("", "");

        requestService.doRequest(get(url + "/" + id), status().isOk(), true); //exists
        requestService.doRequest(delete(url + "/" + id), status().isOk(), true); //deletes
        requestService.doRequest(get(url + "/" + id), status().isBadRequest(), true); //no data

        requestService.doRequest(put(url + "/" + id + "/recover"), status().isOk(), true); //recover

        requestService.doRequest(get(url + "/" + id), status().isOk(), true); //exists
    }

    int createAndPersistPost(String title, String text) {
        Post post = DomainObjectFactory.makePost(title.isBlank() ? genString(10) : title,
                text.isBlank() ? genString(200, 0.1f, true) : text);
        post.setAuthor(mainPerson);
        return postRepository.saveAndFlush(post).getId();
    }
}
