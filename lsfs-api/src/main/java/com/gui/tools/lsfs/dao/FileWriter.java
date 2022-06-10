package com.gui.tools.lsfs.dao;

import java.io.File;
import java.io.IOException;

public interface FileWriter {

    File get(String id);

    String save(File file) throws IOException;

    void delete(String id);

    boolean existsById(String id);
}
