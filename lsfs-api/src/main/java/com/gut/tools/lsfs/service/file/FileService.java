package com.gut.tools.lsfs.service.file;

import com.gut.tools.lsfs.model.file.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    String save(MultipartFile file) throws IOException;

    File getFileByUUID(String uuid) throws IOException;

    void delete(String uuid);

    List<FileMetadata> findAll();
}
