package com.gui.tools.lsfs.dao;

import com.gui.tools.lsfs.model.FileMetadata;

public interface FileDetailsWriter {

    void saveDetails(FileMetadata fileMetadata);

    FileMetadata getByUUID(String uuid);
}
