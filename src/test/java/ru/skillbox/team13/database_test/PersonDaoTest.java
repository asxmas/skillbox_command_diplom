package ru.skillbox.team13.database_test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.skillbox.team13.test_util.DomainObjectFactory.makePerson;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonDaoTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    PersonDAO personDAO;

    int srcid;

    List<Person> persons;

    @BeforeAll
    void prep() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person1 = makePerson();
        em.persist(person1);
        srcid = person1.getId();

        City city1 = new City();
        city1.setTitle("ny");
        em.persist(city1);

        City city2 = new City();
        city2.setTitle("la");
        em.persist(city2);

        Country country = new Country();
        country.setTitle("usa");
        em.persist(country);

        persons = List.of(makePerson(), makePerson(), makePerson());
        persons.get(0).setCity(city1);
        persons.get(0).setCountry(country);
        persons.get(1).setCity(city2);
        persons.get(1).setCountry(country);
        persons.forEach(em::persist);

        persons.stream().map(p -> new Friendship(
                LocalDateTime.now(), "1000", FriendshipStatusCode.FRIEND, person1, p)).forEach(em::persist);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    void testFriends() {
        List<PersonDTO> friends = personDAO.fetchFriendDtos(srcid, FriendshipStatusCode.FRIEND);
        assertEquals(3, friends.size());
        assertEquals(persons.get(0).getFirstName(), friends.get(0).getFirstName());
        assertEquals(persons.get(1).getFirstName(), friends.get(1).getFirstName());
        assertEquals("usa", friends.get(0).getCountryDto().getTitle());
        assertEquals("la", friends.get(1).getCountryDto().getTitle());
    }

    @Test
    void testGetByIds() {
        List<Integer> ids = persons.stream().map(Person::getId).collect(Collectors.toList());
        List<PersonDTO> dtos = personDAO.getById(ids);
        assertEquals(3, dtos.size());
        assertEquals(dtos.get(0).getFirstName(), dtos.get(0).getFirstName());
        assertEquals(dtos.get(1).getFirstName(), dtos.get(1).getFirstName());
        assertEquals("usa", dtos.get(0).getCountryDto().getTitle());
        assertEquals("la", dtos.get(1).getCountryDto().getTitle());
    }
}
