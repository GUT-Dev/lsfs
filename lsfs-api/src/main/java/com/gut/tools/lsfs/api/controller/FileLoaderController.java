package com.gut.tools.lsfs.api.controller;

import com.gut.tools.lsfs.service.FileService;
import com.gut.tools.lsfs.util.CleanupInputStreamResource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("lsfs-api/file")
@RequiredArgsConstructor
public class FileLoaderController {

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<Resource> getByUUID(@RequestParam String uuid) throws IOException {
        File file = fileService.getFileByUUID(uuid);

        InputStreamResource resource = new CleanupInputStreamResource(file);
        MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(file.toPath()));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }

    @PostMapping
    public String loadFile(@RequestBody MultipartFile file) throws IOException {
        return fileService.save(file);
    }

    @DeleteMapping
    public void delete(@RequestParam String uuid) {
        fileService.delete(uuid);
    }
}
