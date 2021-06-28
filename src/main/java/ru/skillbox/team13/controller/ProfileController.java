package ru.skillbox.team13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.service.PostsService;
import ru.skillbox.team13.service.UserService;

@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

  private final PersonService personService;
  private final PostsService postsService;
  private final UserService userService;

  @Autowired
  public ProfileController(PersonService personService, PostsService postsService, UserService userService)  {
    this.personService = personService;
    this.postsService = postsService;
    this.userService = userService;
  }

  @GetMapping("users/me")
  @PreAuthorize("hasAuthority('user')")
  public ResponseEntity<SuccessDto> getCurrentUser(){
    return ResponseEntity.ok(new SuccessDto(userService.getCurrentUserDto()));
  }

  @PutMapping("/users/me")
  public ResponseEntity updateCurrentPerson(@RequestBody PersonDTO personDTO) {
    try {
      personService.updateCurrentPerson(personDTO);
      return ResponseEntity.ok(personDTO);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }
  @DeleteMapping("/users/me")
  public ResponseEntity deleteCurrentPerson()  {
    try {
      return ResponseEntity.ok(personService.deleteCurrentUser(userService.getCurrentUserDto()));
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
  }
  @GetMapping("/users/{id}")
  public ResponseEntity getPersonById(@PathVariable("id") Integer id) {
    try {
      return ResponseEntity.ok(personService.getPersonDTOById(id));
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping("/users/{id}/wall")
  public ResponseEntity getListPosts(@PathVariable("id") Integer id) {
    try {
      PersonDTO personDTO = personService.getPersonDTOById(id);
      return ResponseEntity.ok(postsService.getSetPostsByAuthorId(personDTO.getId()));
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @PostMapping("/users/{id}/wall")
  public ResponseEntity createPost(@PathVariable("id") Integer id, @RequestBody PostDTO postDTO) {
    try {
      Post post = postsService.addPost(postDTO);
      personService.addPostToWall(id, post);
      return ResponseEntity.ok(post);
    }
    catch (Exception ex)  {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
