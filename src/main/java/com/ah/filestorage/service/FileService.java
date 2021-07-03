package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {
    private final FileRepository fileRepository;

    public File save(File file) {
        return fileRepository.save( file );
    }

    public void deleteById(String id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        optionalFile.ifPresent( fileRepository::delete );
    }
}
