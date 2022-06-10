package com.gui.tools.lsfs.exceptions;

public class FileAlreadyExistsException extends RuntimeException {

    public FileAlreadyExistsException() {
        super();
    }

    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
