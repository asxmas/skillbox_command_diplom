package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.exception.UnauthorizedException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.StorageRepository;
import ru.skillbox.team13.service.impl.StorageServiceImpl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/storage/")
@RequiredArgsConstructor
public class StorageController {

    private final StorageServiceImpl storageServiceImpl;

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DTOWrapper> addFile(@RequestBody MultipartFile file) throws ExecutionException, InterruptedException, IOException {
            return new ResponseEntity<>(storageServiceImpl.photoUploadDto(file), HttpStatus.OK);
    }

    @GetMapping
    public String test (@RequestParam int test){
        return "Hello world " + test;
    }
}
