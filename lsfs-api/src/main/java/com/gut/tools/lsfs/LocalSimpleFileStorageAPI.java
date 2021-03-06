package com.gut.tools.lsfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LocalSimpleFileStorageAPI extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LocalSimpleFileStorageAPI.class, args);
    }
}
