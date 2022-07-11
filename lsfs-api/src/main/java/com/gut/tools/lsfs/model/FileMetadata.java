package com.gut.tools.lsfs.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileMetadata implements Serializable {

    private String uuid;

    private String name;

    private String type;

    private String contentType;

    private Long size;

    private boolean archived;

    private Long hashSum;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime loadDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastReadDate;
}
