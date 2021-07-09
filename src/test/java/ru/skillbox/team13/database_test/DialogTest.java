package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Message;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.MessageReadStatus;
import ru.skillbox.team13.repository.MessageRepository;
import ru.skillbox.team13.test_util.DomainObjectFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DialogTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    EntityManagerFactory emf;
    Person p;
    Dialog d;

    @BeforeAll
    void prep() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        p = DomainObjectFactory.makePerson();
        d = new Dialog();
        em.persist(p);
        em.persist(d);
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    void cleanDB() {
        messageRepository.deleteAll();
    }

    @Test
    void setStatusTest() {
        Message m = new Message();
        m.setDialog(d);
        m.setAuthor(p);
        m.setRecipient(p);
        m.setTime(LocalDateTime.now());
        m.setReadStatus(MessageReadStatus.SENT);
        m.setMessageText("hello");

        int id = messageRepository.save(m).getId();

        messageRepository.setStatusForId(MessageReadStatus.READ, id);
        messageRepository.flush();

        Message messageModified = messageRepository.findById(id).get();

        assertEquals(MessageReadStatus.READ, messageModified.getReadStatus());
        assertEquals("hello", messageModified.getMessageText());
    }
}
