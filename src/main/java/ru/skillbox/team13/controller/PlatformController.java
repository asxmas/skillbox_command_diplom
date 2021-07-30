package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.PlatformService;

@RestController
@RequestMapping("/api/v1/platform/")
@RequiredArgsConstructor
public class PlatformController {
    private final PlatformService platformService;

    @GetMapping("languages")
    public ResponseEntity<DTOWrapper> getLanguages(@RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(platformService.findAllLanguages(offset, itemPerPage));
    }

    @GetMapping("countries")
    public ResponseEntity<DTOWrapper> getCountries(@RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(platformService.findAllCountries(offset, itemPerPage));
    }

    @GetMapping("cities")
    public ResponseEntity<DTOWrapper> getCities(@RequestParam(required = false, defaultValue = "0") int offset,
                                                @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(platformService.findAllCities(offset, itemPerPage));
    }

    @GetMapping("list")
    public ResponseEntity<DTOWrapper> getAllAsList() {
        return ResponseEntity.ok(platformService.geAll());
    }
}
