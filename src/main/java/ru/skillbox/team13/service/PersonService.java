package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.repository.CityRepo;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.RepoUser;
import ru.skillbox.team13.service.impl.UserServiceImpl;

import java.util.Set;

@Service
@Setter
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo repoPerson;
    private final CityRepo cityRepo;
    private final UserServiceImpl userService;
    private final PostsService postsService;

    public PersonDTO updateCurrentPerson(PersonDTO personDTO)   {
        PersonDTO currentPersonDTO = userService.getCurrentUserDto();
        Person person = new Person();
        Integer personId = currentPersonDTO.getId();
        if (personId != null)  {
            person = repoPerson.getById(personId);
        }
        fillPersonFields(person, personDTO);
        repoPerson.saveAndFlush(person);
        return PersonMapper.convertPersonToPersonDTO(person);
    }

    public void addPostToWall (int id, Post post)   {
        Person person = repoPerson.getById(id);
        post.setAuthor(person);
        Set<Post> personPosts = person.getPosts();
        personPosts.add(post);
        person.setPosts(personPosts);
        repoPerson.save(person);
    }
    public PersonDTO getPersonDTOById(int id) {
        Person person = repoPerson.getById(id);
        PersonDTO personDTO = PersonMapper.convertPersonToPersonDTO(person);
        return personDTO;
    }

    private void fillPersonFields (Person person, PersonDTO dto)    {
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setPhoto(dto.getPhoto());
        person.setCity(dto.getCity() != null ? cityRepo.getById(dto.getCity().getId()) : null);
    }

    public Person getPersonBiId(int id) {
        return repoPerson.getById(id);
    }

    //todo добавить разлогирование
    //todo проставление флага isArchive

    @Transactional
    public PersonDTO deleteCurrentUser (PersonDTO personDTO) {
        Person person = repoPerson.getById(personDTO.getId());
        postsService.setNullAuthor(personDTO.getId());
        person.setActive(false);
        repoPerson.save(person);
        return personDTO;
    }
}
