package ru.skillbox.team13.controller_integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skillbox.team13.dto.PlatformValuesDto;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Language;
import ru.skillbox.team13.test_util.RequestService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlatformControllerTest {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    RequestService requestService;

    String url = "http://localhost:8080/api/v1/platform/";

    @BeforeAll
    void init() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Language> langs = List.of(new Language("english"), new Language("russian"),
                new Language("urdu"), new Language("pushtu"), new Language("uzbek"));
        langs.forEach(em::persist);

        List<City> cities = List.of(new City("New York"), new City("Berlin"), new City("Paris"),
                new City("London"), new City("Rome"));

        cities.forEach(em::persist);

        List<Country> countries = List.of(new Country("Brazil"), new Country("Australia"),
                new Country("India"), new Country("Canada"), new Country("Japan"));

        countries.forEach(em::persist);

        em.getTransaction().commit();
    }

    @Test
    void testAllData() {
        PlatformValuesDto dto = requestService.getAsPlatformValuesDto(get(url + "list"), true);
        assertEquals(5, dto.getCities().length);
        assertEquals(5, dto.getCountries().length);
        assertEquals(5, dto.getLanguages().length);
    }

    @Test
    void getCities() {
        String str = requestService.getDataAsString(get(url + "cities"), true);
        assertTrue(str.contains("Berlin"));
    }

    @Test
    void getCountries() {
        String str = requestService.getDataAsString(get(url + "countries"), true);
        assertTrue(str.contains("Japan"));
    }

    @Test
    void getLangs() {
        String str = requestService.getDataAsString(get(url + "languages"), true);
        assertTrue(str.contains("urdu"));
    }
}
