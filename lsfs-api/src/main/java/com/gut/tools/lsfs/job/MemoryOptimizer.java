package com.gut.tools.lsfs.job;

import com.gut.tools.lsfs.dao.file.FileMetadataDAO;
import com.gut.tools.lsfs.model.FileMetadata;
import com.gut.tools.lsfs.util.Compressor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemoryOptimizer {

    // 1 minute
    private static final long INIT_DELAY_TIMESTAMP = 60_000;
    // 1 hour
    private static final long RATE_DURATION_TIMESTAMP = 3_600_000;
    // 24 hours
    private static final long OLD_FILE_TIMESTAMP = 86_400_000;

    @Value("${path.files}")
    private File filesDirPath;

    @Value("${path.meta}")
    private File metaDataDirPath;

    private final FileMetadataDAO fileMetadataDAO;

    @Scheduled(initialDelay = INIT_DELAY_TIMESTAMP, fixedRate = RATE_DURATION_TIMESTAMP)
    public void zipFiles() {
        long startExecutionTime = System.currentTimeMillis();
        int archivedFilesCount = 0;

        log.info("Optimizing file storage");
        for (File file : Objects.requireNonNull(metaDataDirPath.listFiles())) {

            FileMetadata fileMetadata = fileMetadataDAO.getById(file.getName().substring(0, file.getName().lastIndexOf(".")));

            if(!fileMetadata.isArchived() && isOld(startExecutionTime, fileMetadata)) {
                Compressor.toZip(new File(filesDirPath + "\\" + fileMetadata.getUuid()));

                fileMetadata.setArchived(true);
                fileMetadataDAO.save(fileMetadata);

                archivedFilesCount++;
            }
        }

        log.info("Archived {} files. Execution time: {}", archivedFilesCount, System.currentTimeMillis() - startExecutionTime);
    }

    public boolean isOld(Long currentTime, FileMetadata file) {
        long createFileTimestamp = file.getLastReadDate()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        return currentTime - createFileTimestamp > OLD_FILE_TIMESTAMP;
    }
}

