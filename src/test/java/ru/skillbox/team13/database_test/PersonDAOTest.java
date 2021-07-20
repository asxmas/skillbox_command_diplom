package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;
import ru.skillbox.team13.test_util.DomainObjectFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Random;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonDAOTest {


    @Autowired
    EntityManagerFactory emf;

    @Autowired
    PersonDAO personDAO;

    int mainId;
    int max = 200;
    int friendsTotal;
    Random random = new Random();

    @BeforeAll
    void prep() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person main = DomainObjectFactory.makePerson();
        em.persist(main);
        mainId = main.getId();

        for (int i = 0; i < max; i++) {
            Person p = DomainObjectFactory.makePerson();
            em.persist(p);
            if (random.nextBoolean()) {
                friendsTotal++;
                Friendship f = DomainObjectFactory.makeFriendship(main, p, FriendshipStatusCode.FRIEND);
                em.persist(f);
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void test1() {
        List<Integer> list = personDAO.getFriendsIds(mainId, FriendshipStatusCode.FRIEND);
        Assertions.assertEquals(friendsTotal, list.size());
    }
}
