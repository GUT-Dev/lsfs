package com.gut.tools.lsfs.dao.file;

import com.gut.tools.lsfs.dao.BaseCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileDAO extends BaseCRUD<File, String> {

    String save(MultipartFile multipartFile);
}
