package com.gui.tools.lsfs.dao;

import com.gui.tools.lsfs.exceptions.FileAlreadyExistsException;
import com.gui.tools.lsfs.exceptions.FileDoesNotExitsException;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileWriterImpl implements FileWriter {

    //TODO: move to general config
    private static final String ROOT_PATH = "data/files/";

    @Override
    @Synchronized
    public File get(String id) {
        File file = new File(ROOT_PATH + id);

        if (file.exists()) {
            return file;
        } else {
            throw new FileDoesNotExitsException("Couldn't found file with id: " + id);
        }
    }

    @Override
    @Synchronized
    public String save(FileInputStream fileInputStream) {
        final UUID uuid = UUID.randomUUID();
        File file = new File(ROOT_PATH + uuid);

        if (!file.exists()) {

            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
//                writer.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileAlreadyExistsException();
        }
    }

    @Override
    @Synchronized
    public void delete(String id) {
        File file = get(id);
        file.delete();
    }

    @Override
    @Synchronized
    public boolean existsById(String id) {
        File file = new File(ROOT_PATH + id);
        return file.exists();
    }
}
