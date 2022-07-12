package com.gut.tools.lsfs.dao.file.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gut.tools.lsfs.dao.file.FileMetadataDAO;
import com.gut.tools.lsfs.model.file.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileMetadataDAOImpl implements FileMetadataDAO {

    private static final String JSON = ".json";

    private final ObjectMapper objectMapper;

    @Value("${path.meta}")
    private String rootPath;

    @Override
    public FileMetadata getById(String uuid) {
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
    public List<FileMetadata> findAll() {
        final File rootDir = new File(rootPath);

        return Arrays.stream(Objects.requireNonNull(rootDir.listFiles()))
                .map(f -> {

                    try {
                        return objectMapper.readValue(f, FileMetadata.class);
                    } catch (IOException e) {
                        log.error("Couldn't read fileMetadata: {}", f.getName());
                        e.printStackTrace();
                        return null;
                    }

                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void save(FileMetadata fileMetadata) {
        String id = fileMetadata.getUuid();

        File file = new File(rootPath + id + JSON);

        try {
            if(file.exists()) {
                Files.delete(file.toPath());
            }

            objectMapper.writeValue(file, fileMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Synchronized
    public void deleteById(String uuid) {
        try {
            Files.delete(Path.of(rootPath + uuid + JSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(String id) {
        File file = new File(rootPath + id);
        return file.exists();
    }
}
