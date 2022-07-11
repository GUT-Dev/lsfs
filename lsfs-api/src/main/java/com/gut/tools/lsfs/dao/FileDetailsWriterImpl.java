package com.gut.tools.lsfs.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gut.tools.lsfs.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDetailsWriterImpl implements FileDetailsWriter {

    private static final String JSON = ".json";

    private final ObjectMapper objectMapper;

    @Value("${path.meta}")
    private String rootPath;

    @Override
    public void saveDetails(FileMetadata fileMetadata) throws IOException {
        String id = fileMetadata.getUuid();

        File file = new File(rootPath + id + JSON);

        if(file.exists()) {
            Files.delete(file.toPath());
        }

        try {
            objectMapper.writeValue(file, fileMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileMetadata getByUUID(String uuid) {
        File file = new File(rootPath + uuid + JSON);

        if(file.exists()) {
            try {
                return objectMapper.readValue(file, FileMetadata.class);
            } catch (IOException e) {
                log.error("Couldn't read fileMetadata: {}", file.getName());
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    @Synchronized
    public void delete(String uuid) throws IOException {
        Files.delete(Path.of(rootPath + uuid + JSON));
    }
}
