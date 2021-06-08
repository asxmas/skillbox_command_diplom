package ru.skillbox.team13.jpatest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.repository.PersonRepo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

    @Test
    void sanityCheck() {
        assertNotNull(personRepo);
    }

    @BeforeAll
    void saveTestPerson() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Bobson");
        person.setRegDate(LocalDateTime.now());
        person.setEmail("e@mail");
        person.setMessagesPermission(PersonMessagePermission.ALL);
        person.setBlocked(false);
        personRepo.save(person);
    }

    @Test
    void testPrecountResults() {
        assertEquals(1, personRepo.countPersons("%bob%"));
    }

    @Test
    void testFindByName() {
        List<Person> people = personRepo.findFriendsByName(PageRequest.of(0, 10), "%bob%");
        assertEquals(1, people.size());
        assertEquals("Bobson", people.get(0).getLastName());
    }
}
