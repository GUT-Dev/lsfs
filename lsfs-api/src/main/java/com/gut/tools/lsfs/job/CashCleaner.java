package com.gut.tools.lsfs.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
@EnableAsync
public class CashCleaner {

//    @Value("${path.temp}")
//    private File tempFilesDirPath;
//
//    // TODO: move this values to configuration
//    @Async
//    @Scheduled(initialDelay = 20000, fixedRate = 10000)
//    public void clean() {
//        for(File file : Objects.requireNonNull(tempFilesDirPath.listFiles())) {
//            file.deleteOnExit();
//        }
//    }
}
