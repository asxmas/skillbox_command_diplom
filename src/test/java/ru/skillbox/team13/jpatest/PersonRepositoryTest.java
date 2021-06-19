package ru.skillbox.team13.jpatest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.DomainObjectFactory;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.repository.PersonRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    void sanityCheck() {
        assertNotNull(personRepository);
    }

    @Test
    @Transactional
    void testPrecountResults() {
        Person p = DomainObjectFactory.makePerson("Bob", "Bobson", "e@mail");
        personRepository.save(p);
        assertEquals(1, personRepository.countPersons("%bob%"));
    }

    @Test
    @Transactional
    void testFindByName() {
        Person p = DomainObjectFactory.makePerson("Bob", "Bobson", "e@mail");
        personRepository.save(p);
        List<Person> people = personRepository.findFriendsByName(PageRequest.of(0, 10), "%bob%");
        assertEquals(1, people.size());
        assertEquals("Bobson", people.get(0).getLastName());
    }
}
