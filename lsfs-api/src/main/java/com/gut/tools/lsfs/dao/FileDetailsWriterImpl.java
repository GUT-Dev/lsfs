package com.gut.tools.lsfs.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gut.tools.lsfs.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDetailsWriterImpl implements FileDetailsWriter {

    private final ObjectMapper objectMapper;

    @Value("${path.meta}")
    private String rootPath;

    @Override
    public void saveDetails(FileMetadata fileMetadata) {
        String id = fileMetadata.getUuid();

        File file = new File(rootPath + id + ".json");

        if(file.exists()) {
            file.delete();
        }

        try {
            objectMapper.writeValue(file, fileMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileMetadata getByUUID(String uuid) {
        File file = new File(rootPath + uuid + ".json");

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
}
