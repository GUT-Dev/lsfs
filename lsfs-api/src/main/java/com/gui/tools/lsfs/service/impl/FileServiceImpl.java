package com.gui.tools.lsfs.service.impl;

import com.gui.tools.lsfs.dao.FileDetailsWriter;
import com.gui.tools.lsfs.dao.FileWriter;
import com.gui.tools.lsfs.exceptions.FileDoesNotExitsException;
import com.gui.tools.lsfs.model.FileMetadata;
import com.gui.tools.lsfs.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    //TODO: move to general config
    private static final String ROOT_PATH = "lsfs-api/data/temp/";

    private final FileWriter fileWriter;
    private final FileDetailsWriter fileDetailsWriter;

    public String save(MultipartFile file) throws IOException {
        final String fileName = file.getResource().getFilename();
        final int formatIndex = fileName.lastIndexOf(".");

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setName(fileName.substring(0, formatIndex));
        fileMetadata.setType(fileName.substring(formatIndex));
        fileMetadata.setContentType(file.getContentType());
        fileMetadata.setSize(file.getSize());
        fileMetadata.setCreatedDate(LocalDateTime.now());
        // TODO: add checkSum here to fileMetadata

        String uuid = fileWriter.save(file);

        fileMetadata.setUuid(uuid);

        fileDetailsWriter.saveDetails(fileMetadata);

        return uuid;
    }

    public File getFileByUUID(String uuid) {
        File savedFile = fileWriter.get(uuid);
        FileMetadata fileMetadata = fileDetailsWriter.getByUUID(uuid);

        if(savedFile == null || fileMetadata == null) {
            throw new FileDoesNotExitsException();
        }

        try {
            File file = new File(ROOT_PATH + fileMetadata.getName() + fileMetadata.getType());
            FileUtils.copyFile(savedFile, file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
