package com.gui.tools.lsfs.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements Serializable {

    private String username;

    private String secretKey;

    private String password;

    private List<FileSpase> spaces;
}
