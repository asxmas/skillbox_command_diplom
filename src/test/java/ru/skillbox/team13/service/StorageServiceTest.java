package ru.skillbox.team13.service;

import com.amazonaws.services.ec2.model.CreateSpotDatafeedSubscriptionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.StorageDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Storage;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.test_util.DomainObjectFactory;
import ru.skillbox.team13.test_util.RequestService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StorageServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    ObjectMapper om;

    @Autowired
    RequestService requestService;

    @Autowired
    EntityManagerFactory emf;

    int personId;
    int storageId;
    int userId;

    @BeforeAll
    void prepare() {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person = DomainObjectFactory.makePerson("Ivan", "Ivanov", "example@mail.ru");
        em.persist(person);

        User user = DomainObjectFactory.makeUser("example@mail.ru", person);
        em.persist(user);

        em.getTransaction().commit();
        em.close();

        userId = user.getId();
        personId = person.getId();
    }

    @Test
    @Transactional
    @WithMockUser(username = "example@mail.ru")
    public void testUploadNotImageFile()
            throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","text.txt", MediaType.TEXT_PLAIN_VALUE, "text".getBytes());
        DTOWrapper storageDto = storageService.photoUploadDto(file);
        assertNull(storageDto);
    }

    @Test
    @Transactional
    @WithMockUser(username = "example@mail.ru")
    public void test() throws Exception {
        MockHttpServletRequestBuilder text = get("http://localhost:8080/api/v1/storage/").param("test", "1");
        String example = "Hello world 1";
        assertEquals(mockMvc.perform(text).andReturn().getResponse().getContentAsString(), example);
    }

    @Test
    @Transactional
    @WithMockUser(username = "example@mail.ru")
    public void testUploadNullFile()
            throws Exception {
        MultipartFile file = new MockMultipartFile("file","image.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        DTOWrapper storageDto = storageService.photoUploadDto(file);
        assertNull(storageDto);
    }

    @Test
    @Transactional
    @WithMockUser(username = "example@mail.ru")
    public void testUploadFile()
            throws Exception {
        MultipartFile file = new MockMultipartFile("file","image.jpeg", MediaType.IMAGE_JPEG_VALUE, "text".getBytes(StandardCharsets.UTF_8));
        DTOWrapper storageDto = storageService.photoUploadDto(file);
        assertNotNull(storageDto);
    }
}
