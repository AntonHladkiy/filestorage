package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.exception.BadRequestException;
import com.ah.filestorage.repository.FileRepository;
import com.ah.filestorage.response.FilePagingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    @Test
    void saveAddsTag( ) {
        File file = new File( "file.txt", 123456789L );
        when( fileRepository.save( file ) ).thenReturn( file );
        File savedFile = fileService.save( file );
        assertTrue( savedFile.getTags( ).contains( "document" ) );
    }

    @Test
    void deleteById( ) {
        File file = new File( "file.txt", 123456789L );
        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );
        assertDoesNotThrow( ( ) -> fileService.deleteById( file.getId( ) ) );
    }

    @Test
    void assignTagsById( ) {
        File file = new File( "file.txt", 123456789L );
        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );

        String tag1 = "tag12", tag2 = "tag24";
        fileService.addTagsById( file.getId( ), new HashSet<>( Arrays.asList( "tag12", "tag24" ) ) );

        assertTrue( file.getTags( ).contains( tag1 ) );
        assertTrue( file.getTags( ).contains( tag2 ) );
        assertEquals( 2, file.getTags( ).size( ) );
    }

    @Test
    void removeTagsById( ) {
        File file = new File( "file.txt", 123456789L );
        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );

        Set<String> tags = new HashSet<>( Arrays.asList( "tag12", "tag24" ) );
        file.setTags( tags );
        fileService.removeTagsById( file.getId( ), new HashSet<>( Arrays.asList( "tag12", "tag24" ) ) );

        assertEquals( 0, file.getTags( ).size( ) );
    }

    @Test()
    void removeTagsByIdBadRequest( ) {
        File file = new File( "file.txt", 123456789L );
        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );

        Set<String> tags = new HashSet<>( Arrays.asList( "tag12", "tag24" ) );
        file.setTags( tags );

        assertThrows( BadRequestException.class,
                ( ) -> fileService.removeTagsById( file.getId( ),
                        new HashSet<>( Arrays.asList( "tag24", "tag36" ) ) ) );
        assertEquals( 1, file.getTags( ).size( ) );
    }

    @Test
    void listFilesWithPagination( ) {
        File file = new File( "file.txt", 123456789L );
        file.getTags( ).add( "tag12" );

        PageImpl<File> page = new PageImpl<>( Collections.singletonList( file ) );
        when( fileRepository.findAllByNameContains( any( ), any( PageRequest.class ) ) ).thenReturn( page );

        FilePagingResponse expectedPage = new FilePagingResponse( 1, page.getContent( ) );
        FilePagingResponse actualPage = fileService.filesWithPagination( 0, 10, "","" );

        assertEquals( expectedPage.getTotalPages( ), actualPage.getTotalPages( ) );
        assertEquals( expectedPage.getPage( ), actualPage.getPage( ) );
    }
}
