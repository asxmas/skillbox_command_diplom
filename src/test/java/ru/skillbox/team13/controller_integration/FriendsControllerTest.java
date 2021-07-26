package ru.skillbox.team13.controller_integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.test_util.DomainObjectFactory;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserFriendshipStatusDTO;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.repository.FriendshipRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skillbox.team13.entity.enums.FriendshipStatusCode.FRIEND;
import static ru.skillbox.team13.entity.enums.FriendshipStatusCode.REQUEST;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class FriendsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FriendshipRepository frRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    ObjectMapper om;

    @Autowired
    EntityManagerFactory emf;

    private int bobId;
    private int jimId;
    private int timId;

    @Test
    void sanityCheck() {
        assertNotNull(personRepository);
        assertNotNull(userRepo);
        assertNotNull(mockMvc);
        assertNotNull(om);
        assertNotNull(emf);
        EntityManager em = emf.createEntityManager();
        assertNotNull(em);
        em.close();
    }

    @BeforeAll
    void prepareTheUser() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person bobPerson = DomainObjectFactory.makePerson("Bob", "Bobson", "bob@mail");
        em.persist(bobPerson);

        User bobUser = DomainObjectFactory.makeUser("bob@mail");
        bobUser.setPerson(bobPerson);
        em.persist(bobUser);

        em.getTransaction().commit();
        em.close();

        bobId = bobPerson.getId();
    }

    @BeforeAll
    void preparePersons() {
        Person person2 = DomainObjectFactory.makePerson("Jim", "Jimson", "@2");
        person2.setBlocked(false);

        Person person3 = DomainObjectFactory.makePerson("Tim", "Timson", "@3");
        person3.setBlocked(false);

        jimId = personRepository.save(person2).getId();
        timId = personRepository.save(person3).getId();
    }

    @AfterAll
    void destroy() {
        friendshipRepository.deleteAll();
        userRepo.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "bob@mail")
        //using 'email' as internal user name
    void testIAmIn() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/friends"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testGetFriends() throws JsonProcessingException {
        makeFriendship(bobId, jimId, FRIEND, "from bob to jim");
        makeFriendship(bobId, timId, FRIEND, "from bob to tim");

        DTOWrapper dto = performRequest(get("http://localhost:8080/api/v1/friends"));
        assertEquals(2, dto.getTotal());
        PersonDTO[] payload = om.readValue(om.writeValueAsString(dto.getData()), PersonDTO[].class);
        assertEquals(2, payload.length);
        assertTrue(Arrays.stream(payload).anyMatch(d -> d.getFirstName().equals("Jim")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testGetFriendsWithPartialName() throws JsonProcessingException {
        makeFriendship(bobId, jimId, FRIEND, "from bob to jim");
        makeFriendship(bobId, timId, FRIEND, "from bob to tim");

        DTOWrapper dto = performRequest(get("http://localhost:8080/api/v1/friends").param("name", "son"));

        PersonDTO[] payload = om.readValue(om.writeValueAsString(dto.getData()), PersonDTO[].class);
        assertEquals(2, payload.length);
        assertTrue(Arrays.stream(payload).anyMatch(d -> d.getLastName().equals("Timson")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testGetDeleteFriend() throws JsonProcessingException {
        makeFriendship(bobId, jimId, FRIEND, "from bob to jim");
        makeFriendship(bobId, timId, FRIEND, "from bob to tim");

        DTOWrapper dto = performRequest(delete("http://localhost:8080/api/v1/friends/" + timId));
        MessageDTO payload = om.readValue(om.writeValueAsString(dto.getData()), MessageDTO.class);
        assertEquals("ok", payload.getMessage());

        DTOWrapper dto2 = performRequest(get("http://localhost:8080/api/v1/friends"));
        assertEquals(1, dto2.getTotal());
        PersonDTO[] payload2 = om.readValue(om.writeValueAsString(dto2.getData()), PersonDTO[].class);
        assertEquals("Jimson", payload2[0].getLastName());
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testAcceptFriend() throws JsonProcessingException {
        makeFriendship(jimId, bobId, FriendshipStatusCode.REQUEST, "from jim to bob");

        int countREQUSTfromJim = frRepo.countRequestedFriendships(jimId, REQUEST);
        assertEquals(1, countREQUSTfromJim);

        DTOWrapper dto = performRequest(post("http://localhost:8080/api/v1/friends/" + jimId));
        MessageDTO payload = om.readValue(om.writeValueAsString(dto.getData()), MessageDTO.class);
        assertEquals("ok", payload.getMessage());


        countREQUSTfromJim = frRepo.countRequestedFriendships(jimId, REQUEST);
        assertEquals(0, countREQUSTfromJim);
        int countFRIENDfromJim = frRepo.countRequestedFriendships(jimId, FRIEND);
        assertEquals(1, countFRIENDfromJim);
        int countREQUSTfromBob = frRepo.countRequestedFriendships(bobId, REQUEST);
        assertEquals(1, countREQUSTfromBob);
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testGetIncomingFriendsRequests() throws JsonProcessingException {
        makeFriendship(jimId, bobId, REQUEST, "from jim to bob");
        makeFriendship(timId, bobId, FRIEND, "from tim to bob");

        DTOWrapper dto = performRequest(get("http://localhost:8080/api/v1/friends/request"));
        assertEquals(1, dto.getTotal());
        PersonDTO[] payload = om.readValue(om.writeValueAsString(dto.getData()), PersonDTO[].class);
        assertEquals(1, payload.length);
        assertTrue(Arrays.stream(payload).anyMatch(d -> d.getFirstName().equals("Jim")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testGetIncomingFriendsRequestsByName() throws JsonProcessingException {
        makeFriendship(jimId, bobId, REQUEST, "from jim to bob");
        makeFriendship(timId, bobId, FRIEND, "from tim to bob");

        DTOWrapper dto = performRequest(get("http://localhost:8080/api/v1/friends/request").param("name", "son"));
        assertEquals(1, dto.getTotal());
        PersonDTO[] payload = om.readValue(om.writeValueAsString(dto.getData()), PersonDTO[].class);
        assertEquals(1, payload.length);
        assertTrue(Arrays.stream(payload).anyMatch(d -> d.getFirstName().equals("Jim")));
    }

    @Test
    @Transactional
    @WithMockUser(username = "bob@mail")
    void testIsFriends() throws JsonProcessingException {
        makeFriendship(jimId, bobId, REQUEST, "from jim to bob");
        makeFriendship(timId, bobId, FRIEND, "from tim to bob");

        int[] ids = new int[]{jimId, timId};

        DTOWrapper dto = performRequest(post("http://localhost:8080/api/v1/is/friends")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(ids)));

        UserFriendshipStatusDTO[] statuses = om.readValue(om.writeValueAsString(dto.getData()), UserFriendshipStatusDTO[].class);

        assertEquals(2, statuses.length);
        assertEquals("REQUEST", Arrays.stream(statuses).filter(s -> s.getUserId() == jimId).findFirst().get().getStatus());
        assertEquals("FRIEND", Arrays.stream(statuses).filter(s -> s.getUserId() == timId).findFirst().get().getStatus());
    }


    private void makeFriendship(int from, int to, FriendshipStatusCode code, String statusName) {
        Person fromP = personRepository.findById(from).get();
        Person toP = personRepository.findById(to).get();
        Friendship friendship = new Friendship(LocalDateTime.now(), statusName, code, fromP, toP);
        frRepo.save(friendship);
    }

    @SneakyThrows
    DTOWrapper performRequest(RequestBuilder req) {
        String json = mockMvc.perform(req)
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return om.readValue(json, DTOWrapper.class);
    }
}
