package com.gut.tools.lsfs.util;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class CleanupInputStreamResource extends InputStreamResource {
    public CleanupInputStreamResource(File file) throws FileNotFoundException {
        super(new FileInputStream(file) {
            @Override
            public void close() throws IOException {
                super.close();
                Files.delete(file.toPath());
            }
        });
    }
}
