package ru.skillbox.team13.integr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.repository.PersonRepo;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FriendsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    ObjectMapper om;

    @Test
    void sanityTest() {
        assertNotNull(mockMvc);
        assertNotNull(personRepo);
        assertNotNull(om);
    }

    @BeforeAll
    void preparePerson() {
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
    @WithMockUser
    void findFriendTest() throws Exception {
        MvcResult res = mockMvc.perform(get("http://localhost:8080/api/v1/friends").param("name", "bob"))
                .andExpect(status().isOk()).andReturn();
        String json = res.getResponse().getContentAsString(StandardCharsets.UTF_8);

        DTOWrapper dto = om.readValue(json, DTOWrapper.class);
        PersonDTO personDTO = om.readValue(om.writeValueAsString(dto.getData()[0]), PersonDTO.class);

        assertEquals("Bob", personDTO.getFirstName());
    }
}
