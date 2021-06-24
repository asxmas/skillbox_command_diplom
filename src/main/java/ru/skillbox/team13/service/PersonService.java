package ru.skillbox.team13.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.repository.CityRepo;
import ru.skillbox.team13.repository.PersonRepo;

import java.util.Set;

@Service
@Setter
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;
    private final CityRepo cityRepo;
    private final UserService userService;

    public PersonDTO updateCurrentPerson(PersonDTO personDTO)   {
        PersonDTO currentPersonDTO = userService.getCurrentUserDto();
        Person person = new Person();
        Integer personId = currentPersonDTO.getId();
        if (personId != null)  {
            person = personRepo.getById(personId);
        }
        fillPersonFields(person, personDTO);
        personRepo.saveAndFlush(person);
        return PersonMapper.convertPersonToPersonDTO(person);
    }

    public void addPostToWall (int id, Post post)   {
        Person person = personRepo.getById(id);
        post.setAuthor(person);
        Set<Post> personPosts = person.getPosts();
        personPosts.add(post);
        person.setPosts(personPosts);
        personRepo.save(person);
    }
    public PersonDTO getPersonDTOById(int id) {
        Person person = personRepo.getById(id);
        PersonDTO personDTO = PersonMapper.convertPersonToPersonDTO(person);
        return personDTO;
    }

    private void fillPersonFields (Person person, PersonDTO dto)    {
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setPhoto(dto.getPhoto());
        person.setCity(dto.getCity() != null ? cityRepo.getById(dto.getCity().getId()) : null);
    }

    public Person getPersonBiId(int id) {
        return personRepo.getById(id);
    }

    public PersonDTO deleteCurrentUser (PersonDTO personDTO) {
        Person currentPerson = personRepo.getById(personDTO.getId());
        personRepo.delete(currentPerson);
        return personDTO;
    }
}
