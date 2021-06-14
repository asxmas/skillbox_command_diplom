package ru.skillbox.team13.controller;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.exception.SuccessResponse;
import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.service.PostsService;

@RestController
@RequestMapping("/api/v1/")
public class PersonController {

  private final PersonService personService;
  private final PostsService postsService;

  @Autowired
  public PersonController (PersonService personService, PostsService postsService)  {
    this.personService = personService;
    this.postsService = postsService;
  }

  @PutMapping("/users/me")
  public ResponseEntity<SuccessResponse>  updateCurrentPerson(@RequestBody PersonDTO personDTO) {
    try {
      personService.updatePerson(personDTO);
      return ResponseEntity.ok(new SuccessResponse());
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }

  //todo Доделать
  @DeleteMapping("/users/me")
  public ResponseEntity<SuccessResponse> deleteCurrentPerson(@RequestBody PersonDTO personDTO)  {
    try {
      personService.getPersonById(personDTO.getId());
      return ResponseEntity.ok(new SuccessResponse());
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }
  //todo Доделать
  @GetMapping("/users/{id}")
  public ResponseEntity<SuccessResponse> getPersonById(@PathVariable("id") Integer id) {
    try {
      Person person = personService.getPersonById(id);
      return ResponseEntity.ok(new SuccessResponse());
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  //todo Доделать
  @GetMapping("/users/{id}/wall")
  public ResponseEntity<SuccessResponse> getListPosts(@PathVariable("id") Integer id) {
    try {
      Person person = personService.getPersonById(id);
      return ResponseEntity.ok(new SuccessResponse());
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //todo Доделать убрать логику
  @PostMapping("/users/{id}/wall")
  public ResponseEntity<SuccessResponse> createPost(@PathVariable("id") Integer id,
                                         @RequestBody PostDTO postDTO) {
    try {
      Post post = postsService.addPost(postDTO);
      personService.addPostToWall(id, post);
      return ResponseEntity.ok(new SuccessResponse());
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
