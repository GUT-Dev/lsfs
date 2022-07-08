package com.gut.tools.lsfs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Setter
@Jacksonized
public class ErrorModel {
    private LocalDateTime time;
    private String message;
}
