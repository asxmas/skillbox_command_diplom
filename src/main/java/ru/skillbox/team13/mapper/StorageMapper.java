package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.StorageDto;
import ru.skillbox.team13.entity.Storage;

@RequiredArgsConstructor
public class StorageMapper {
    public static StorageDto mapStorageToStorageDto(Storage entity){
        return StorageDto.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .relativeFilePath(entity.getRelativeFilePath())
                .fileFormat(entity.getFileFormat())
                .bytes(entity.getBytes())
                .fileType(entity.getFileType())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

