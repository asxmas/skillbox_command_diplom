package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
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
public class CommentTest {

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

        Comment comment1 = makeComment("comment 1 to post 1", person, posts.get(0));
        Comment comment2 = makeComment("comment 2 to post 2", person, posts.get(1));
        Comment comment3 = makeComment("comment 3 to post 2", person, posts.get(1));
        Comment comment4 = makeComment("comment 4 to post 3 parent", person, posts.get(2));
        Comment comment5 = makeComment("comment 5 to post 3 child", person, posts.get(2));

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
    @Transactional
    void getSingleCommentForSinglePost() {
        int postId = posts.get(0).getId();
        List<CommentDto> commentDTOS = commentDAO.getCommentDtosForPostIds(postId);

        assertEquals(1, commentDTOS.size());
        assertEquals("comment 1 to post 1", commentDTOS.get(0).getText());
        assertNull(commentDTOS.get(0).getParentId());
        assertEquals(authorId, commentDTOS.get(0).getAuthorId());
        assertEquals(postId, commentDTOS.get(0).getPostId());
    }

    @Test
    @Transactional
    void getMultipleCommentsForSinglePost() {
        int postId = posts.get(1).getId();
        List<CommentDto> commentDTOS = commentDAO.getCommentDtosForPostIds(postId);

        assertEquals(2, commentDTOS.size());
        assertEquals("comment 2 to post 2", commentDTOS.get(0).getText());
        assertEquals("comment 3 to post 2", commentDTOS.get(1).getText());
        assertEquals(authorId, commentDTOS.get(0).getAuthorId());
        assertEquals(authorId, commentDTOS.get(1).getAuthorId());
        assertEquals(postId, commentDTOS.get(0).getPostId());
        assertEquals(postId, commentDTOS.get(1).getPostId());
    }

    @Test
    @Transactional
    void getCommentsForMultiplePosts() {
        int postId1 = posts.get(0).getId();
        int postId2 = posts.get(1).getId();
        List<CommentDto> commentDTOS = commentDAO.getCommentDtosForPostIds(List.of(postId1, postId2));

        assertEquals(3, commentDTOS.size());
    }

    @Test
    @Transactional
    void getParentChildComments() {
        int pId = posts.get(2).getId();
        List<CommentDto> commentDTOS = commentDAO.getCommentDtosForPostIds(pId);

        CommentDto parent = commentDTOS.get(0);
        CommentDto child = commentDTOS.get(1);

        assertEquals(2, commentDTOS.size());
        assertEquals("comment 4 to post 3 parent", parent.getText());
        assertEquals("comment 5 to post 3 child", child.getText());
        assertNull(parent.getParentId());
        assertNotNull(child.getParentId());
        assertEquals(parent.getId(), child.getParentId());
    }

    @Test
    void getCommentsWithPageable() {
        Pageable p = PageUtil.getPageable(100, 20);
        Page<CommentDto> page = commentDAO.getCommentDtosForPostIds(posts.get(3).getId(), p);
        assertEquals(200, page.getTotalElements());
        assertEquals(20, page.getContent().size());
    }
}
