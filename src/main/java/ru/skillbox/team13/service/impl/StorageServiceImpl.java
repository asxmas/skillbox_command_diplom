package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.imgscalr.Scalr;
import org.jetbrains.annotations.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Storage;
import ru.skillbox.team13.mapper.StorageMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.StorageRepository;
import ru.skillbox.team13.service.StorageService;
import ru.skillbox.team13.component.FileStore;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final FileStore fileStore;
    private final StorageRepository storageRepository;
    private final UserServiceImpl userService;

    @Transactional
    public DTOWrapper photoUploadDto (String fileType, MultipartFile file) throws IOException {
        if (file.getSize() == 0) {
            return null;
        }
        if(!isImage(file)){
            return null;
        }
        //create vars
        UUID uuid = UUID.randomUUID();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        Person person = userService.getAuthorizedUser().getPerson();

        //todo переделать под orderby
        //create storage
        Storage storage = new Storage();
        storage.setFileName(fileName);
        storage.setFileFormat(Objects.requireNonNull(contentType).replaceAll(".+/", ""));
        storage.setBytes(file.getSize());
        storage.setRelativeFilePath(String.format("%s/%s/%s", "https://socnet-storage.s3.eu-west-2.amazonaws.com", uuid, fileName));
        storage.setRawFileURL(String.format("%s/%s/%s", "https://socnet-storage.s3.eu-west-2.amazonaws.com", uuid, fileName));
        storage.setFileType("IMAGE");
        storage.setCreatedAt(LocalDateTime.now());
        storage.setOwnerId(person.getId());
        storage.setPerson(person);
        storageRepository.save(storage);

        //upload files
        String pathWithoutName = String.format("%s/%s","socnet-storage", uuid);
        uploadFile(file, pathWithoutName);

        //compresfile and upload compresfile
        uploadFile(imgResize(file), pathWithoutName);

        return WrapperMapper.wrap(StorageMapper.mapStorageToStorageDto(storage), true);
    }

    public Storage getStorage(int id){
        return storageRepository.findById(id);
    }

    private MultipartFile imgResize(MultipartFile file) throws IOException {
        BufferedImage scaledImage = Scalr.resize(ImageIO.read(file.getInputStream()), 200, 200, Scalr.OP_ANTIALIAS);
        ByteArrayOutputStream resizingImageInBufImage = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, "png",resizingImageInBufImage);
        resizingImageInBufImage.close();

        InputStream inputStream = new ByteArrayInputStream(resizingImageInBufImage.toByteArray());
        return getNewCompressedFile(file, resizingImageInBufImage.toByteArray(), inputStream);

    }

    private void uploadFile(MultipartFile file, String pathWithoutName) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String nameOfFile = String.format("%s", file.getOriginalFilename());
        fileStore.upload(pathWithoutName, nameOfFile, Optional.of(metadata), file.getInputStream());
    }

    private boolean isImage(MultipartFile file){
        return Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType());
    }

    private MultipartFile getNewCompressedFile(MultipartFile file, byte[] bytes, InputStream inputStream){
        return new MultipartFile() {
            @NotNull
            @Override
            public String getName() {
                return "comp." + file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return "comp." + file.getOriginalFilename();
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return file.isEmpty();
            }

            @Override
            public long getSize() {
                return 0;
            }

            @NotNull
            @Override
            public byte[] getBytes() {
                return bytes;
            }

            @NotNull
            @Override
            public InputStream getInputStream() {
                return inputStream;
            }

            @Override
            public void transferTo(@NotNull File file) throws IllegalStateException, UnsupportedOperationException {
            }
        };
    }
}
