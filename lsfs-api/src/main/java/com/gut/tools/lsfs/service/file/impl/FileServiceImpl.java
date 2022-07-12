package com.gut.tools.lsfs.service.file.impl;

import com.gut.tools.lsfs.dao.file.FileMetadataDAO;
import com.gut.tools.lsfs.dao.file.FileDAO;
import com.gut.tools.lsfs.exceptions.LSFSStorageException;
import com.gut.tools.lsfs.model.file.FileMetadata;
import com.gut.tools.lsfs.service.file.FileService;
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
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${path.temp}")
    private String tempDirPath;

    @Value("${path.files}")
    private String filesDirPath;

    private final FileDAO fileDAO;
    private final FileMetadataDAO fileMetadataDAO;

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

        String uuid = fileDAO.save(file);

        fileMetadata.setHashSum(FileUtils.checksumCRC32(new File(filesDirPath + uuid)));
        fileMetadata.setUuid(uuid);

        fileMetadataDAO.save(fileMetadata);

        return uuid;
    }

    public File getFileByUUID(String uuid) {
        FileMetadata fileMetadata = fileMetadataDAO.getById(uuid);

        if(fileMetadata.isArchived()) {
            Compressor.unZip(new File(filesDirPath + uuid + ".zip"));
            fileMetadata.setArchived(false);
            fileMetadataDAO.save(fileMetadata);
        }

        File savedFile = fileDAO.getById(uuid);

        if(savedFile == null) {
            throw new LSFSStorageException("Couldn't found file with UUID: [" + uuid + "]");
        }

        try {
            File file = new File(tempDirPath + fileMetadata.getName() + fileMetadata.getType());
            FileUtils.copyFile(savedFile, file);

            fileMetadata.setLastReadDate(LocalDateTime.now());
            fileMetadataDAO.save(fileMetadata);

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(String uuid) {
        try {
            fileDAO.deleteById(uuid);
            fileMetadataDAO.deleteById(uuid);

            File file = new File(tempDirPath + uuid);
            if(file.exists()) {
                Files.delete(file.toPath());
            }
        } catch (IOException e) {
            throw new LSFSStorageException("Couldn't delete file with uuid: [" + uuid + "]");
        }
    }

    @Override
    public List<FileMetadata> findAll() {
        return fileMetadataDAO.findAll();
    }
}
