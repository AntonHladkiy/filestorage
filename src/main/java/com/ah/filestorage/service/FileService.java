package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.exception.BadRequestException;
import com.ah.filestorage.exception.NotFoundException;
import com.ah.filestorage.repository.FileRepository;
import com.ah.filestorage.response.FilePagingResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {
    private final FileRepository fileRepository;
    private final ExtensionService extensionService;
    public File save(File file) {
        extensionService.addTag( file );
        return fileRepository.save( file );
    }

    public void deleteById(String id) {
        Optional<File> optionalFile = fileRepository.findById( id );
        if (optionalFile.isPresent( )) {
            fileRepository.delete( optionalFile.get( ) );
        } else {
            throw new NotFoundException( );
        }
    }


    public void addTagsById(String id, Set<String> tags) {
        Optional<File> optionalFile = fileRepository.findById( id );
        if (optionalFile.isPresent( )) {
            File file = optionalFile.get( );
            file.getTags( ).addAll( tags );
            fileRepository.save( file );
        } else {
            throw new NotFoundException( );
        }
    }

    public void removeTagsById(String id, Set<String> tags) {
        Optional<File> optionalFile = fileRepository.findById( id );
        if (optionalFile.isPresent( )) {
            File file = optionalFile.get( );
            Set<String> fileTags = file.getTags( );
            for ( String tag : tags ) {
                if (fileTags.contains( tag )) {
                    fileTags.remove( tag );
                } else {
                    throw new BadRequestException( "tag not found on file" );
                }
            }
            fileRepository.save( file );
        } else {
            throw new NotFoundException( );
        }

    }

    public FilePagingResponse filesWithPagination(int page, int size, String name, String tags) {
        name = name.replaceAll( "([\\r\\n])", "" );
        Page<File> filesPage;
        if (tags == null || tags.length( ) == 0) {
            filesPage = fileRepository.findAllByNameContains( name, PageRequest.of( page, size ) );
        } else {
            Set<String> tagsSet = new HashSet<>( Arrays.asList( tags.split( "," ) ) );
            filesPage = fileRepository.findAllByNameContainsAndTags( name, tagsSet, PageRequest.of( page, size ) );
        }
        return new FilePagingResponse( filesPage.getTotalElements( ), filesPage.getContent( ) );
    }
}
