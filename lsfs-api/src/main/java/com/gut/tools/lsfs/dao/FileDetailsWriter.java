package com.gut.tools.lsfs.dao;

import com.gut.tools.lsfs.model.FileMetadata;

import java.io.IOException;

public interface FileDetailsWriter {

    void saveDetails(FileMetadata fileMetadata) throws IOException;

    FileMetadata getByUUID(String uuid);

    void delete(String uuid) throws IOException;
}
