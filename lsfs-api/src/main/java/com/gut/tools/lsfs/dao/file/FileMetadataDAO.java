package com.gut.tools.lsfs.dao.file;

import com.gut.tools.lsfs.dao.BaseCRUD;
import com.gut.tools.lsfs.model.file.FileMetadata;

public interface FileMetadataDAO extends BaseCRUD<FileMetadata, String> {

    void save(FileMetadata fileMetadata);
}
