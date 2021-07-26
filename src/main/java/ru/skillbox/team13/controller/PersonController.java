package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.EditPersonDto;
import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class PersonController {

    private final PersonService personService;
    private final UserService userService;

    @GetMapping("me")
    @PreAuthorize("hasAuthority('user')") //todo preauth??
    //Получить текущего пользователя
    public ResponseEntity<DTOWrapper> getMyProfile() {
        return ResponseEntity.ok(personService.getMyProfile());
    }

    @PutMapping("me")
    //Редактирование текущего пользователя
    public ResponseEntity<DTOWrapper> updateMyProfile(@RequestBody EditPersonDto personDTO) {
        return ResponseEntity.ok(personService.updateMyProfile(personDTO));
    }

    @DeleteMapping("me")
    //Удаление текущего пользователя
    public ResponseEntity<DTOWrapper> deleteMyProfile() {
        DTOWrapper w = personService.deleteMyProfile();
        return ResponseEntity.ok(w);
    }

    @GetMapping("{id}")
    //Получить пользователя по id
    public ResponseEntity<DTOWrapper> getUserProfile(@PathVariable("id") int id) {
        return ResponseEntity.ok(personService.getProfile(id));
    }
}
