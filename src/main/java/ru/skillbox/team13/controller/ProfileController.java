package ru.skillbox.team13.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProfileController {

  private final UserService userService;

  @GetMapping("users/me")
  public ResponseEntity<SuccessDto> getCurrentUser(){
    return ResponseEntity.ok(new SuccessDto(userService.getCurrentUserDto()));
  }

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
