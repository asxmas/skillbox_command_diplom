package ru.skillbox.team13.controllers;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;

@RestController
public class ProfileController {

  @PutMapping("/api/v1/users/me")
  public List<Person> updateCurrentPerson(Person person) {
    return null;
  }

  @DeleteMapping("/api/v1/users/me")
  public void deleteCurrentPerson(Person person)  {
  }

  @GetMapping("/api/v1/users/{id}")
  @ResponseBody
  public ResponseEntity<Integer> getBalanceAccount(@PathVariable("id") Integer id) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Response some", "RestController");
    return null;
  }


}
