package com.gui.tools.lsfs.exceptions;

public class FileDoesNotExitsException extends RuntimeException {
    
    public FileDoesNotExitsException() {
        super();
    }

    public FileDoesNotExitsException(String message) {
        super(message);
    }
}
