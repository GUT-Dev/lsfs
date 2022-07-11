package com.gut.tools.lsfs.service.impl;

import com.gut.tools.lsfs.dao.FileDetailsWriter;
import com.gut.tools.lsfs.dao.FileWriter;
import com.gut.tools.lsfs.exceptions.LSFSStorageException;
import com.gut.tools.lsfs.model.FileMetadata;
import com.gut.tools.lsfs.service.FileService;
import com.gut.tools.lsfs.util.Compressor;
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
    private String tempDirPath;

    @Value("${path.files}")
    private String filesDirPath;

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
        fileMetadata.setLoadDate(LocalDateTime.now());
        fileMetadata.setLastReadDate(LocalDateTime.now());
        fileMetadata.setArchived(false);

        String uuid = fileWriter.save(file);

        fileMetadata.setHashSum(FileUtils.checksumCRC32(new File(filesDirPath + uuid)));
        fileMetadata.setUuid(uuid);

        fileDetailsWriter.saveDetails(fileMetadata);

        return uuid;
    }

    public File getFileByUUID(String uuid) throws IOException {
        FileMetadata fileMetadata = fileDetailsWriter.getByUUID(uuid);

        File savedFile = fileWriter.get(uuid, fileMetadata);

        if(savedFile == null) {
            throw new LSFSStorageException("Couldn't found file with UUID: [" + uuid + "]");
        }

        try {
            File file = new File(tempDirPath + fileMetadata.getName() + fileMetadata.getType());
            FileUtils.copyFile(savedFile, file);

            fileMetadata.setLastReadDate(LocalDateTime.now());
            fileDetailsWriter.saveDetails(fileMetadata);

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

            File file = new File(tempDirPath + uuid);
            if(file.exists()) {
                Files.delete(file.toPath());
            }
        } catch (IOException e) {
            throw new LSFSStorageException("Couldn't delete file with uuid: [" + uuid + "]");
        }
    }
}
