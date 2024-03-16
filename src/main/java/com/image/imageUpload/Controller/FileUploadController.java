package com.image.imageUpload.Controller;

import com.image.imageUpload.entity.FileEntity;
import com.image.imageUpload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            service.saveFile(file.getOriginalFilename(), file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file.");
        }
    }

    @GetMapping("/files/{id}")//This method shows the Image file
    public ResponseEntity<byte[]> getFiles(@PathVariable int id) {
        Optional<FileEntity> optionalFile = service.getFile(id);
        if (optionalFile.isPresent()) {
            FileEntity file = optionalFile.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Change to appropriate image format
            headers.setContentLength(file.getFile().length);
            return new ResponseEntity<>(file.getFile(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/music/{id}")//This method downloads the music file/ Image file
    public ResponseEntity<Resource> getFileById(@PathVariable int id) {
        Optional<FileEntity> optionalFile = service.getFile(id);
        if (optionalFile.isPresent()) {
            FileEntity file = optionalFile.get();
            ByteArrayResource resource = new ByteArrayResource(file.getFile());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set content type as binary data

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
