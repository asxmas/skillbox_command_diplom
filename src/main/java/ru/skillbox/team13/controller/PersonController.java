package ru.skillbox.team13.controller;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.RepoPost;
import ru.skillbox.team13.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class PersonController {

  private final PersonRepo personRepo;
  private final PersonService personService;
  private final RepoPost repoPost;

  @PutMapping("/users/me")
  public ResponseEntity<PersonDTO>  updateCurrentPerson(@RequestBody PersonDTO personDTO) {
    try {
      personService.setPerson(PersonMapper.convertPersonDTOToPerson(personDTO));
      personService.updatePerson();
      return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  //todo Доделать
  @DeleteMapping("/users/me")
  @ResponseBody
  public ResponseEntity<PersonDTO> deleteCurrentPerson(PersonDTO personDTO)  {
    try {
      personRepo.deleteById(personDTO.getId());
      return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }
  //todo Доделать
  @GetMapping("/users/{id}")
  @ResponseBody
  public ResponseEntity<Person> getPersonById(@PathVariable("id") Integer id) {
    try {
      Person person = personRepo.getById(id);
      return new ResponseEntity<>(person, HttpStatus.OK);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  //todo Доделать
  @GetMapping("/users/{id}/wall")
  @ResponseBody
  public ResponseEntity<Set<Post>> getListPosts(@PathVariable("id") Integer id) {
    try {
      Person person = personRepo.getById(id);
      return new ResponseEntity<>(person.getPosts(), HttpStatus.OK);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //todo Доделать убрать логику
  @PostMapping("/users/{id}/wall")
  @ResponseBody
  public ResponseEntity<Post> createPost(@PathVariable("id") Integer id,
                                         PostDTO postDTO) {
    try {
      Post post = PostMapper.convertPostDTOtoPost(postDTO);
      Person person = personRepo.getById(id);
      personService.setPerson(person);
      personService.addPostToWall(post);
      return new ResponseEntity<>(post, HttpStatus.OK);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
