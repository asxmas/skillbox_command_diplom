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
import ru.skillbox.team13.dto.AddCommentDto;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.test_util.RequestService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skillbox.team13.test_util.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentControllerTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ObjectMapper om;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestService requestService;

    Person mainPerson;
    String url;

    int tenthCommentId;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        mainPerson = makePerson();
        em.persist(mainPerson);

        Post post = makePost(mainPerson);
        em.persist(post);

        url = "http://localhost:8080/api/v1/post/" + post.getId() + "/comments";

        for (int i = 0; i < 50; i++) {
            Comment comment = makeComment(mainPerson, post);
            em.persist(comment);
            if (i == 9) tenthCommentId = comment.getId();
        }

        User user = makeUser("main@mail", mainPerson);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    void destroy() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void getCommentsSimpleAndCheckFields() {
        List<CommentDto> comments = requestService.getAsCommentDtoList(get(url), false);
        assertEquals(20, comments.size());
        CommentDto singleComment = comments.get(0);
        assertNotNull(singleComment.getText());
        assertNotNull(singleComment.getPostId());
        assertNotNull(singleComment.getParentId());
        assertNotNull(singleComment.getTime());
        assertNotNull(singleComment.getAuthor());
        assertNotNull(singleComment.getBlocked());
        assertNotNull(singleComment.getComments());
        assertNotNull(singleComment.getLikedByMe());
    }

    @Test
    void getCommentsWithPagination() {
        List<CommentDto> comments = requestService.getAsCommentDtoList(get(url)
                .param("offset", "12").param("itemPerPage", "7"), false);
        assertEquals(7, comments.size());
    }

    @Test
    void getCommentsWithOverPagination() {
        List<CommentDto> comments = requestService.getAsCommentDtoList(get(url)
                .param("offset", "45").param("itemPerPage", "10"), false);
        assertEquals(10, comments.size());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testPostComment() throws JsonProcessingException {
        AddCommentDto payload = new AddCommentDto();
        payload.setCommentText("Hello");

        CommentDto commentDto = requestService.getAsCommentDto(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertEquals("Hello", commentDto.getText());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testPostCommentToComment() throws JsonProcessingException {
        AddCommentDto payload = new AddCommentDto();
        payload.setCommentText("Comment to comment #10");
        payload.setParentId(tenthCommentId);

        CommentDto commentDto = requestService.getAsCommentDto(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertEquals("Comment to comment #10", commentDto.getText());
        assertEquals(tenthCommentId, commentDto.getParentId());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testEditComment() throws JsonProcessingException {
        AddCommentDto payload = new AddCommentDto();
        payload.setCommentText("Edited comment #10");

        CommentDto commentDto = requestService.getAsCommentDto(put(url + "/" + tenthCommentId)
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(payload)), true);

        assertEquals("Edited comment #10", commentDto.getText());
    }

    @Test
    @WithMockUser(username = "main@mail")
    void testDeleteRestore() {
        int total = requestService.getAsCommentDtoList(get(url).param("itemPerPage", String.valueOf(100)), false).size();
        assertEquals(50, total);

        //deleting
        requestService.doRequest(delete(url + "/" + tenthCommentId), status().isOk(), true);

        total = requestService.getAsCommentDtoList(get(url).param("itemPerPage", String.valueOf(100)), false).size();
        assertEquals(49, total);

        //restoring
        requestService.doRequest(put(url + "/" + tenthCommentId + "/recover"), status().isOk(), true);

        total = requestService.getAsCommentDtoList(get(url).param("itemPerPage", String.valueOf(100)), false).size();
        assertEquals(50, total);
    }
}
