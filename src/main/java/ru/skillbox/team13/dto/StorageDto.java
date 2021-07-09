package ru.skillbox.team13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.team13.entity.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StorageDto {

    private String fileName;
    private String relativeFilePath;
    private String rawFileURL;
    private String fileFormat;
    private long bytes;
    private String fileType;
    private LocalDateTime createdAt;


}