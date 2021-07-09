package ru.skillbox.team13.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.team13.dto.DTOWrapper;

import java.io.IOException;

public interface StorageService {
    DTOWrapper photoUploadDto (MultipartFile file) throws IOException;
}
