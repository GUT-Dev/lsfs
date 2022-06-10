package com.gui.tools.lsfs.model;

import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileMetadata implements Serializable {

    private String name;

    private String size;

    private String hashSum;

    private File file;

    private LocalDateTime createdDate;
}
