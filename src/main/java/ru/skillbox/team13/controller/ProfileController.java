package ru.skillbox.team13.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PersonDTO;

@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

  @PutMapping("/users/me")
  public List<PersonDTO> updateCurrentPerson(PersonDTO personDTO) {
    return null;
  }

  @DeleteMapping("/users/me")
  public void deleteCurrentPerson(PersonDTO personDTO)  {
  }

  @GetMapping("/users/{id}")
  @ResponseBody
  public ResponseEntity<Integer> getUserById(@PathVariable("id") Integer id) {
    return null;
  }


}
