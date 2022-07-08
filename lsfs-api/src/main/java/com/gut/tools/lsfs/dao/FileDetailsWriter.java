package com.gut.tools.lsfs.dao;

import com.gut.tools.lsfs.model.FileMetadata;

public interface FileDetailsWriter {

    void saveDetails(FileMetadata fileMetadata);

    FileMetadata getByUUID(String uuid);
}
