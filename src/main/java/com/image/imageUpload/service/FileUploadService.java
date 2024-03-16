package com.image.imageUpload.service;

import com.image.imageUpload.entity.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface FileUploadService {

    public void saveFile(String fileName, MultipartFile file) throws IOException;

    public Optional<FileEntity> getFile(int id);
}
