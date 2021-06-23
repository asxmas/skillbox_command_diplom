package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.platform.CityService;
import ru.skillbox.team13.service.platform.CountryService;
import ru.skillbox.team13.service.platform.LanguageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/v1/platform/")
@RequiredArgsConstructor
public class PlatformController {
    private final CityService cityService;
    private final CountryService countryService;
    private final LanguageService languageService;

    @GetMapping("languages")
    public ResponseEntity<DTOWrapper> ResponseLanguages(@RequestParam String name,
                                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                                        @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<DTOWrapper>(languageService.findAllLanguages(name, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("countries")
    public ResponseEntity<DTOWrapper> ResponseCountries(@RequestParam String name,
                                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                                        @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<DTOWrapper>(countryService.findAllCountries(name, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("cities")
    public ResponseEntity<DTOWrapper> ResponseCities(@RequestParam String name,
                                                     @RequestParam(required = false, defaultValue = "0") int offset,
                                                     @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<DTOWrapper>(cityService.findAllCities(name, offset, itemPerPage), HttpStatus.OK);
    }
}
