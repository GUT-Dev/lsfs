package com.gut.tools.lsfs.util;

import lombok.Synchronized;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compressor {

    @Synchronized
    public static void toZip(File file) {
        try {
            if(file.exists()) {
                FileOutputStream fos = new FileOutputStream(file.getPath() + ".zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                zipOut.close();
                fis.close();
                fos.close();

                FileUtils.delete(file);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Synchronized
    public static void unZip(File zip) {
        final File unzipFile = new File(zip.getPath().substring(0, zip.getPath().length() - 4));

        try {
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.isDirectory()) {
                    if (!unzipFile.isDirectory() && !unzipFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + unzipFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = unzipFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(unzipFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();

            FileUtils.delete(zip);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
