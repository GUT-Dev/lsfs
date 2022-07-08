package com.gut.tools.lsfs.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    String save(MultipartFile file) throws IOException;

    File getFileByUUID(String uuid);
}
