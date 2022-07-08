package com.gut.tools.lsfs.dao;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileWriter {

    File get(String id);

    String save(MultipartFile multipartFile) throws IOException;

    void delete(String id) throws IOException;

    boolean existsById(String id);
}
