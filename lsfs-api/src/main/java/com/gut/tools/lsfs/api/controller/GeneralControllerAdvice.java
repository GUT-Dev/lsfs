package com.gut.tools.lsfs.api.controller;

import com.gut.tools.lsfs.exceptions.LSFSException;
import com.gut.tools.lsfs.exceptions.LSFSStorageException;
import com.gut.tools.lsfs.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ LSFSException.class, LSFSStorageException.class })
    public ResponseEntity<ErrorModel> handleLSFSException(LSFSException ex) {
        ErrorModel err = new ErrorModel();
        err.setTime(LocalDateTime.now());
        err.setMessage(ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
