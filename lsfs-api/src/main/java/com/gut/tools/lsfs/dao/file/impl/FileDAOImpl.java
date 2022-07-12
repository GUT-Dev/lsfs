package com.gut.tools.lsfs.dao.file.impl;

import com.gut.tools.lsfs.dao.file.FileDAO;
import com.gut.tools.lsfs.exceptions.LSFSException;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDAOImpl implements FileDAO {

    @Value("${path.files}")
    private String rootPath;

    @Synchronized
    public File getById(String uuid) {
        File file = new File(rootPath + uuid);

        if (file.exists()) {
            return file;
        } else {
            log.warn("Couldn't found file with UUID: " + uuid);
            return null;
        }
    }

    @Override
    public List<File> findAll() {
        final File rootDir = new File(rootPath);
        return Arrays.stream(Objects.requireNonNull(rootDir.listFiles())).toList();
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
    public void deleteById(String uuid) {
        try {
            File file = new File(rootPath + uuid);

            if(file.exists()) {
                Files.delete(file.toPath());
            } else {
                Files.delete(Path.of(rootPath + uuid + ".zip"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Synchronized
    public boolean existsById(String id) {
        File file = new File(rootPath + id);
        return file.exists();
    }
}
