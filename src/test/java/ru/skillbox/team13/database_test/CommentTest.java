package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;

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

    Integer authorId;
    List<Integer> postIds = new ArrayList<>();

    @BeforeAll
    void prepare() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = makePerson("jim", genString(5), genString(10));
        em.persist(person);
        authorId = person.getId();

        Post post = makePost(genString(20), genString(20), person);
        em.persist(post);

        postIds.add(post.getId());

        Comment comment = makeComment("comment 1 to post 1", person, post);
        em.persist(comment);

        Post post2 = makePost(genString(20), genString(20), person);
        em.persist(post2);

        postIds.add(post2.getId());

        Comment comment2 = makeComment("comment 2 to post 2", person, post2);
        em.persist(comment2);

        Comment comment3 = makeComment("comment 3 to post 2", person, post2);
        em.persist(comment3);

        Post post3 = makePost(genString(20), genString(20), person);
        em.persist(post3);

        postIds.add(post3.getId());

        Comment comment4 = makeComment("comment 4 to post 3 parent", person, post3);
        em.persist(comment4);

        Comment comment5 = makeComment("comment 5 to post 3 child", person, post3);
        comment5.setParent(comment4);
        em.persist(comment5);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @Transactional
    void getSingleCommentForSinglePost() {
        int postId = postIds.get(0);
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
        int postId = postIds.get(1);
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
        int postId1 = postIds.get(0);
        int postId2 = postIds.get(1);
        List<CommentDto> commentDTOS = commentDAO.getCommentDtosForPostIds(List.of(postId1, postId2));

        assertEquals(3, commentDTOS.size());
    }

    @Test
    @Transactional
    void getParentChildComments() {
        int pId = postIds.get(2);
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
}
