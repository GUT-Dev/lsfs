package com.gut.tools.lsfs.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FileSpase implements Serializable {

    private String name;

    private List<FileMetadata> files;
}
