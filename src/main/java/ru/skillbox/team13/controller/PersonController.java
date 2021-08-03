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
    public ResponseEntity<DTOWrapper> updateMyProfile(@RequestBody EditPersonDto dto) {
        String fName = dto.getFirstName();
        String lName = dto.getLastName();
        String about = dto.getAbout();
        String city = dto.getCity();
        String country = dto.getCountry();
        String phone = dto.getPhone();
        Long bdate = dto.getBirthDate();
        String photo = dto.getPhoto();

        return ResponseEntity.ok(personService.updateMyProfile(fName, lName, about, city, country, photo, phone, bdate));
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

    @GetMapping("search")
    public ResponseEntity<DTOWrapper> findUser(@RequestParam(name = "first_name", required = false) String firstName,
                                               @RequestParam(name = "last_name", required = false) String lastName,
                                               @RequestParam(name = "age_from", required = false) Integer ageFrom,
                                               @RequestParam(name = "age_to", required = false) Integer ageTo,
                                               @RequestParam(required = false) String country,
                                               @RequestParam(required = false) String city,
                                               @RequestParam(required = false, defaultValue = "0") int offset,
                                               @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(personService.find(firstName, lastName, ageFrom, ageTo, country, city, offset, itemPerPage));
    }
}
