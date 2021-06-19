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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.DomainObjectFactory;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LikeDto;
import ru.skillbox.team13.dto.LikesDto;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LikeControllerTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    LikeRepository likeRepo;

    int userId;
    int personId;
    int postId;
    int commentId;


    @BeforeAll
    void constructUserPersonPostComment() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = DomainObjectFactory.makePerson("Person", "McPerson", "user@mail.com");
        em.persist(person);

        User user = DomainObjectFactory.makeUser("user@mail.com");
        user.setPerson(person);
        em.persist(user);

        Post post = DomainObjectFactory.makePost("some title", "some text");
        post.setAuthor(person);
        em.persist(post);

        Comment comment = DomainObjectFactory.makeComment("hello");
        comment.setPost(post);
        comment.setAuthor(person);
        em.persist(comment);

        em.getTransaction().commit();

        userId = user.getId();
        personId = person.getId();
        postId = post.getId();
        commentId = comment.getId();

        em.close();
    }

    @Test
    @WithMockUser(username = "user@mail.com")
    void upAndRunning() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/liked")
                .param("item_id", "12345").param("type", "lol"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void findMyLike() throws Exception {
        Person person = personRepo.findById(personId).get();
        Post post = postRepo.findById(postId).get();

        Like like = new Like();
        like.setPerson(person);
        like.setPostOrComment(post);
        like.setTime(LocalDateTime.now());
        likeRepo.save(like);

        //default (takes personId from current user, 'liked by me')
        RequestBuilder r = get("http://localhost:8080/api/v1/liked")
                .param("item_id", String.valueOf(postId)).param("type", "Post");

        DTOWrapper resp = performRequest(r);
        LikesDto payload = om.readValue(om.writeValueAsString(resp.getData()), LikesDto.class);

        assertTrue((Boolean) payload.getLikes());

        //with explicit person_id
        r = get("http://localhost:8080/api/v1/liked")
                .param("user_id", String.valueOf(personId))
                .param("item_id", String.valueOf(postId)).param("type", "Post");

        resp = performRequest(r);
        payload = om.readValue(om.writeValueAsString(resp.getData()), LikesDto.class);

        assertTrue((Boolean) payload.getLikes());

        //with wrong person_id
        mockMvc.perform(get("http://localhost:8080/api/v1/liked")
                .param("user_id", "100000")
                .param("item_id", String.valueOf(postId)).param("type", "Post")).andExpect(status().isBadRequest());


        //with separate existing person
        Person p = DomainObjectFactory.makePerson("a", "b", "c");
        int pId = personRepo.save(p).getId();

        r = get("http://localhost:8080/api/v1/liked")
                .param("user_id", String.valueOf(pId))
                .param("item_id", String.valueOf(postId)).param("type", "Post");


        resp = performRequest(r);
        payload = om.readValue(om.writeValueAsString(resp.getData()), LikesDto.class);

        assertFalse((Boolean) payload.getLikes());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void findMyLikeOnComment() throws JsonProcessingException {
        Person person = personRepo.findById(personId).get();
        Comment comment = commentRepo.findById(commentId).get();

        Like like = new Like();
        like.setPerson(person);
        like.setPostOrComment(comment);
        like.setTime(LocalDateTime.now());
        likeRepo.save(like);

        RequestBuilder r = get("http://localhost:8080/api/v1/liked")
                .param("user_id", String.valueOf(personId))
                .param("item_id", String.valueOf(commentId))
                .param("type", "Comment");

        DTOWrapper resp = performRequest(r);
        LikesDto payload = om.readValue(om.writeValueAsString(resp.getData()), LikesDto.class);

        assertTrue((Boolean) payload.getLikes());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void testGetLikedBy() throws JsonProcessingException {
        Person person = personRepo.findById(personId).get();
        Post post = postRepo.findById(postId).get();
        Like like = new Like();
        like.setPerson(person);
        like.setPostOrComment(post);
        like.setTime(LocalDateTime.now());
        likeRepo.save(like);

        RequestBuilder req = get("http://localhost:8080/api/v1/likes")
                .param("item_id", String.valueOf(postId)).param("type", "Post");

        DTOWrapper dto = performRequest(req);
        LikesDto payload = om.readValue(om.writeValueAsString(dto.getData()), LikesDto.class);

        assertEquals(1, (int) (payload.getLikes()));
        assertEquals(personId, payload.getUsers()[0]);
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void testMakeLikeSuccess() throws JsonProcessingException {
        LikeDto likeDto = new LikeDto(postId, "Post");
        String reqJson = om.writeValueAsString(likeDto);

        RequestBuilder rb = put("http://localhost:8080/api/v1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson);

        DTOWrapper resp = performRequest(rb);
        LikesDto respDto = om.readValue(om.writeValueAsString(resp.getData()), LikesDto.class);

        assertEquals(1, (int)(respDto.getLikes()));
        assertEquals(personId, respDto.getUsers()[0]);
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void testMakeDoubleLike() throws Exception {
        LikeDto likeDto = new LikeDto(postId, "Post");
        String reqJson = om.writeValueAsString(likeDto);

        RequestBuilder rb = put("http://localhost:8080/api/v1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson);

        performRequest(rb); //first pass

        mockMvc.perform(put("http://localhost:8080/api/v1/likes") //second pass
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void testDislikeError() throws Exception {

        mockMvc.perform(delete("http://localhost:8080/api/v1/likes")
                .param("item_id", String.valueOf(postId)).param("type", "Post"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user@mail.com")
    void testLikeDislike() throws Exception {

        LikeDto likeDto = new LikeDto(postId, "Post");
        String reqJson = om.writeValueAsString(likeDto);

        RequestBuilder rb = put("http://localhost:8080/api/v1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson);

        LikesDto respDto = om.readValue(om.writeValueAsString(performRequest(rb).getData()), LikesDto.class);
        assertEquals(1, (int)(respDto.getLikes()));


        rb = delete("http://localhost:8080/api/v1/likes")
                .param("item_id", String.valueOf(postId)).param("type", "Post");

        respDto = om.readValue(om.writeValueAsString(performRequest(rb).getData()), LikesDto.class);
        assertEquals(0, (int)(respDto.getLikes()));
    }

    @SneakyThrows
    DTOWrapper performRequest(RequestBuilder req) {
        String json = mockMvc.perform(req)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return om.readValue(json, DTOWrapper.class);

    }
}
