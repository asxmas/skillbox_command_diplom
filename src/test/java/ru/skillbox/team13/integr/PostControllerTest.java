package ru.skillbox.team13.integr;

import org.junit.jupiter.api.Assertions;
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
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.skillbox.team13.DomainObjectFactory.genString;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerTest {

    @Autowired
    EntityManagerFactory emf;

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

    //todo MOAR!

    @Test
    @WithMockUser(username = "main@mail")
    void testSimpleFind() {
        Post post = DomainObjectFactory.makePost("substring", "substring");
        post.setAuthor(mainPerson);
        postRepository.saveAndFlush(post);

        DTOWrapper w = requestService.getAsWrapper(get(url).param("text", "substring"), false);
        Assertions.assertEquals(1, w.getTotal());
    }
}
