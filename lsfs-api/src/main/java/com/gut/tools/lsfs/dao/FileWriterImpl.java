package com.gut.tools.lsfs.dao;

import com.gut.tools.lsfs.exceptions.LSFSException;
import com.gut.tools.lsfs.exceptions.LSFSStorageException;
import com.gut.tools.lsfs.model.FileMetadata;
import com.gut.tools.lsfs.util.Compressor;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileWriterImpl implements FileWriter {

    @Value("${path.files}")
    private String rootPath;

    private final FileDetailsWriter fileDetailsWriter;

    @Override
    @Synchronized
    public File get(String uuid, FileMetadata fileMetadata) throws IOException {
        File file = new File(rootPath + uuid);

        if(fileMetadata.isArchived()) {
            Compressor.unZip(new File(file.getPath() + ".zip"));
            fileMetadata.setArchived(false);
            fileDetailsWriter.saveDetails(fileMetadata);
        }

        if (file.exists()) {
            return file;
        } else {
            throw new LSFSStorageException("Couldn't found file with UUID: " + uuid);
        }
    }

    @Override
    @Synchronized
    public String save(MultipartFile multipartFile) {
        final UUID uuid = UUID.randomUUID();
        File file = new File(rootPath + uuid);

        if (!file.exists()) {
            try {
                Files.copy(multipartFile.getInputStream(), Paths.get(file.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new LSFSException("File with UUID: [" + uuid + "] already exists");
        }
        return file.getName();
    }

    @Override
    @Synchronized
    public void delete(String uuid) throws IOException {
        Files.delete(Path.of(rootPath + uuid));
    }

    @Override
    @Synchronized
    public boolean existsById(String id) {
        File file = new File(rootPath + id);
        return file.exists();
    }
}
