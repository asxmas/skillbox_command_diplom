package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "relative_file_path", nullable = false)
    private String relativeFilePath;

    @Column(name = "raw_file_url", nullable = false)
    private String rawFileURL;

    @Column(name = "file_format", nullable = false)
    private String fileFormat;

    @Column(name = "bytes", nullable = false)
    private long bytes;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "owner_id", nullable = false)
    private int ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

}
