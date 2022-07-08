package com.gut.tools.lsfs.service.impl;

import com.gut.tools.lsfs.dao.FileDetailsWriter;
import com.gut.tools.lsfs.dao.FileWriter;
import com.gut.tools.lsfs.exceptions.LSFSStorageException;
import com.gut.tools.lsfs.model.FileMetadata;
import com.gut.tools.lsfs.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${path.temp}")
    private String rootPath;

    private final FileWriter fileWriter;
    private final FileDetailsWriter fileDetailsWriter;

    public String save(MultipartFile file) throws IOException {
        final String fileName = file.getResource().getFilename();
        final int formatIndex = Objects.requireNonNull(fileName).lastIndexOf(".");

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setName(fileName.substring(0, formatIndex));
        fileMetadata.setType(fileName.substring(formatIndex));
        fileMetadata.setContentType(file.getContentType());
        fileMetadata.setSize(file.getSize());
        fileMetadata.setCreatedDate(LocalDateTime.now());
        // TODO: add checkSum here to fileMetadata

        String uuid = fileWriter.save(file);

        fileMetadata.setUuid(uuid);

        fileDetailsWriter.saveDetails(fileMetadata);

        return uuid;
    }

    public File getFileByUUID(String uuid) {
        File savedFile = fileWriter.get(uuid);
        FileMetadata fileMetadata = fileDetailsWriter.getByUUID(uuid);

        if(savedFile == null || fileMetadata == null) {
            throw new LSFSStorageException("Couldn't found file with UUID: [" + uuid + "]");
        }

        try {
            File file = new File(rootPath + fileMetadata.getName() + fileMetadata.getType());
            FileUtils.copyFile(savedFile, file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(String uuid) {
        try {
            fileWriter.delete(uuid);
            fileDetailsWriter.delete(uuid);

            // TODO: remove after add cache cleaner
            File file = new File(rootPath + uuid);
            if(file.exists()) {
                Files.delete(file.toPath());
            }
        } catch (IOException e) {
            throw new LSFSStorageException("Couldn't delete file with uuid: [" + uuid + "]");
        }
    }
}
