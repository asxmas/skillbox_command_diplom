package ru.skillbox.team13.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Storage;

import java.io.IOException;

public interface StorageService {
    DTOWrapper photoUploadDto (String fileType, MultipartFile file) throws IOException;
    Storage getStorage(int id);
}
