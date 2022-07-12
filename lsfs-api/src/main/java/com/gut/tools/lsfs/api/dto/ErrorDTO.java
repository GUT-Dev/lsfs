package com.gut.tools.lsfs.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Setter
@Jacksonized
public class ErrorDTO {
    private LocalDateTime time;
    private String message;
}
