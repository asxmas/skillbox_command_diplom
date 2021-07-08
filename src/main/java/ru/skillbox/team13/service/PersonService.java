package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.service.impl.UserServiceImpl;
import ru.skillbox.team13.util.TimeUtil;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo repoPerson;
    private final UserServiceImpl userService;
    private final PostsService postsService;
    private final CityService cityService;
    private final CountryService countryService;

    public PersonDTO updateCurrentPerson(PersonDTO personDTO)   {
        PersonDTO currentPersonDTO = userService.getCurrentUserDto();
        personDTO = fillPersonDTOFields(personDTO);
        Person person = new Person();
        Integer personId = currentPersonDTO.getId();
        if (personId != null)  {
            person = repoPerson.getById(personId);
        }
        fillPersonFields(person, personDTO);
        repoPerson.saveAndFlush(person);
        return PersonMapper.convertPersonToPersonDTO(person);
    }

    private PersonDTO fillPersonDTOFields(PersonDTO personDTO)    {
        CityDto cityDTO = PersonMapper.convertCityToCityDTO(cityService.getById(personDTO.getTownId()));
        CountryDto countryDto = PersonMapper.convertCountryToCountryDTO(countryService.getCountryById(personDTO.getCountryId()));
        personDTO.setCityDto(cityDTO);
        personDTO.setCountryDto(countryDto);
        personDTO.setBirthDateLDT(TimeUtil.toLocalDateTime(personDTO.getBirthDate()));
        return personDTO;
    }

    public void addPostToWall (int id, PostDTO postDTO)   {
        Person person = repoPerson.getById(id);
        PersonDTO personDTO = PersonMapper.convertPersonToPersonDTO(person);
        postDTO.setAuthor(personDTO);
        Post post = postsService.addPost(postDTO, person);
        Set<Post> personPosts = person.getPosts();
        personPosts.add(post);
        person.setPosts(personPosts);
        repoPerson.save(person);
    }
    public PersonDTO getPersonDTOById(int id) {
        Person person = repoPerson.getById(id);
        return PersonMapper.convertPersonToPersonDTO(person);
    }

    public Person getById(int id)   {
        return repoPerson.getById(id);
    }

    private void fillPersonFields (Person person, PersonDTO dto)    {
        if (dto.getFirstName() != null) {
            person.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            person.setLastName(dto.getLastName());
        }
        if (dto.getBirthDateLDT() != null) {
            person.setBirthDate(dto.getBirthDateLDT());
        }
        if (dto.getPhone() != null) {
            person.setPhone(dto.getPhone());
        }
        if (dto.getPhoto() != null) {
            person.setPhoto(dto.getPhoto());
        }
        if (dto.getMessagesPermission() != null)    {
            person.setMessagesPermission(dto.getMessagesPermission());
        }
        if (dto.getTownId() != null)    {
            person.setCity(cityService.getById(dto.getTownId()));
        }
        if (dto.getCountryId() != null)    {
            person.setCountry(countryService.getCountryById(dto.getCountryId()));
        }
        if (dto.getBirthDateLDT() != null) {
            person.setBirthDate(dto.getBirthDateLDT());
        }
    }

    @Transactional
    public PersonDTO deleteCurrentUser (PersonDTO personDTO) {
        Person person = repoPerson.getById(personDTO.getId());
        postsService.setInactiveAuthor();
        person.setArchive(true);
        repoPerson.save(person);
        return personDTO;
    }
}
