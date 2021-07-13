package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.EditPersonDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class PersonController {

    private final PersonService profileService;

    @GetMapping("me")
    @PreAuthorize("hasAuthority('user')") //todo preauth??
    //Получить текущего пользователя
    public ResponseEntity<DTOWrapper> getMyProfile() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }

    @PutMapping("me")
    //Редактирование текущего пользователя
    public ResponseEntity<DTOWrapper> updateMyProfile(@RequestBody EditPersonDto personDTO) {
        return ResponseEntity.ok(profileService.updateMyProfile(personDTO));
    }

    @DeleteMapping("me")
    //Удаление текущего пользователя
    public ResponseEntity<DTOWrapper> deleteMyProfile(HttpServletRequest request) throws ServletException {
            DTOWrapper w = profileService.deleteMyProfile();
            log.debug("Logging out.");
            request.logout();
            return ResponseEntity.ok(w);
    }

    @GetMapping("{id}")
    //Получить пользователя по id
    public ResponseEntity<DTOWrapper> getUserProfile(@PathVariable("id") int id) {
        return ResponseEntity.ok(profileService.getProfile(id));
    }
}
