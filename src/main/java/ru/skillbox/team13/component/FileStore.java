package ru.skillbox.team13.component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class FileStore {

    private final AmazonS3 amazonS3;

        public void upload(String path,
                           String fileName,
                           Optional<Map<String, String>> optionalMetaData,
                           InputStream inputStream) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            optionalMetaData.ifPresent(map -> {
                if (!map.isEmpty()) {
                    map.forEach(objectMetadata::addUserMetadata);
                }
            });
            try {
                amazonS3.putObject(path, fileName, inputStream, objectMetadata);
            } catch (AmazonServiceException e) {
                throw new IllegalStateException("Failed to upload the file", e);
            }
        }


}
