package com.image.imageUpload.service;

import com.image.imageUpload.entity.FileEntity;
import com.image.imageUpload.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void saveFile(String fileName, MultipartFile file) throws IOException {
        FileEntity entity = new FileEntity();
        entity.setName(fileName);
        entity.setFile(file.getBytes());
        fileRepository.save(entity);
    }

    @Override
    public Optional<FileEntity> getFile(int id) {
        return fileRepository.findById(id);
    }
}
