package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.StorageDto;
import ru.skillbox.team13.entity.Storage;

import java.time.LocalDateTime;


public class StorageMapper {
    public static StorageDto mapStorageToStorageDto(Storage entity){
        StorageDto storageDto = new StorageDto();
        storageDto.setFileName(entity.getFileName());
        storageDto.setRelativeFilePath(entity.getRelativeFilePath());
        storageDto.setRawFileURL(entity.getRawFileURL());
        storageDto.setFileFormat(entity.getFileFormat());
        storageDto.setBytes(entity.getBytes());
        storageDto.setFileType(entity.getFileType());
        storageDto.setCreatedAt(entity.getCreatedAt());

        return storageDto;
    }
}

