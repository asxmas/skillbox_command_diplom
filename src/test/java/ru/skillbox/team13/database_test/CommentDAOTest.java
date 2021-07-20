package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;
import ru.skillbox.team13.util.PageUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skillbox.team13.test_util.DomainObjectFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentDAOTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PostRepository postRepository;

    Integer authorId;
    List<Post> posts = new ArrayList<>();

    Person person;
    Comment comment1;
    Comment comment2;
    Comment comment3;
    Comment comment4;
    Comment comment5;

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        person = makePerson();
        em.persist(person);
        authorId = person.getId();

        for (int i = 0; i < 4; i++) {
            Post post = makePost(person);
            em.persist(post);
            posts.add(post);
        }

        comment1 = makeComment("comment 1 to post 1", person, posts.get(0));
        comment2 = makeComment("comment 2 to post 2", person, posts.get(1));
        comment3 = makeComment("comment 3 to post 2", person, posts.get(1));
        comment4 = makeComment("comment 4 to post 3 parent", person, posts.get(2));
        comment5 = makeComment("comment 5 to post 3 child", person, posts.get(2));

        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);
        em.persist(comment4);

        comment5.setParent(comment4);
        em.persist(comment5);

        for (int i = 0; i < 200; i++) {
            Comment c = makeComment(person, posts.get(3));
            em.persist(c);
        }

        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    void destroy() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    void getCommentsForMultiplePosts() {
        List<Integer> ids = List.of(posts.get(0).getId(), posts.get(1).getId(), posts.get(2).getId());
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(0, ids);

        assertEquals(5, commentDtos.size());
    }

    @Test
    void getManyCommentsForOnePost() {
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(0, List.of(posts.get(3).getId()));
        assertEquals(200, commentDtos.size());
    }

    @Test
    void getMultipleCommentsWrongIDs() {
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(0, List.of(1000, 3000, 5000));
        assertTrue(commentDtos.isEmpty());
    }

    @Test
    void getSingleCommentSimple() {
        int id = comment1.getId();
        List<CommentDto> c = commentDAO.getCommentDTO(0, id);
        assertEquals(1, c.size());
    }

    @Test
    void getSingleCommentWChildren() {
        int id = comment4.getId();
        List<CommentDto> c = commentDAO.getCommentDTO(0, id);
        assertEquals(2, c.size());
        assertNull(c.get(0).getParentId());
        assertNotNull(c.get(1).getParentId());
        assertTrue(c.get(1).getComments().isEmpty());
        assertEquals(c.get(0).getPostId(), c.get(1).getPostId());
    }

    @Test
    void getSingleChildComment() {
        int id = comment5.getId();
        List<CommentDto> c = commentDAO.getCommentDTO(0, id);
        assertEquals(1, c.size());
        assertEquals("comment 5 to post 3 child", c.get(0).getText());
        assertNotNull(c.get(0).getParentId());
    }

    @Test
    void getSingleCommentWrongID() {
        List<CommentDto> c = commentDAO.getCommentDTO(0, 100500);
        assertTrue(c.isEmpty());
    }

    @Test
    void getCommentsWithPageable() {
        Page<CommentDto> page = commentDAO.getCommentDTOs(0, posts.get(3).getId(), PageUtil.getPageable(100, 20));
        assertEquals(200, page.getTotalElements());
        assertEquals(20, page.getContent().size());
    }
}
