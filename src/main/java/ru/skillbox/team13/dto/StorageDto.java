package ru.skillbox.team13.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageDto {

    private int id;
    private String fileName;
    private String relativeFilePath;
    private String rawFileURL;
    private String fileFormat;
    private long bytes;
    private String fileType;
    private LocalDateTime createdAt;

}