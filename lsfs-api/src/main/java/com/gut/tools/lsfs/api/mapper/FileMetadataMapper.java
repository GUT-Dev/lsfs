package com.gut.tools.lsfs.api.mapper;

import com.gut.tools.lsfs.api.dto.FileMetadataDTO;
import com.gut.tools.lsfs.model.file.FileMetadata;
import org.mapstruct.Mapper;

@Mapper
public interface FileMetadataMapper {

    FileMetadataDTO toDto(FileMetadata obj);
}
