package com.ah.filestorage.controller;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.repository.FileRepository;
import com.ah.filestorage.response.ErrorResponse;
import com.ah.filestorage.response.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FileControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private FileRepository fileRepository;

    private final ObjectMapper mapper = new ObjectMapper( );

    @Test
    void saveFile( ) throws Exception {
        File file = new File( "file", 123456789L );
        String json = mapper.writeValueAsString( file );
        doReturn( file ).when( fileRepository ).save( any( ) );

        MockHttpServletRequestBuilder request = post( "/file" )
                .content( json )
                .contentType( MediaType.APPLICATION_JSON );
        MockMvc mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
                .defaultRequest( get( "/file" ) )
                .build( );

        ResultActions resultActions = mvc.perform( request );

        Map<String, String> expectedResult = new HashMap<>( );
        expectedResult.put( "ID", file.getId( ) );
        String expectedJson = mapper.writeValueAsString( expectedResult );

        resultActions.andExpect( status( ).isOk( ) ).andExpect( content( ).string( expectedJson ) );
    }

    @Test
    void deleteExistingFile( ) throws Exception {
        File file = new File( "file", 123456789L );

        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );
        doNothing( ).when( fileRepository ).delete( any( ) );

        MockHttpServletRequestBuilder request = delete( "/file/{ID}", "id" );
        MockMvc mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
                .defaultRequest( get( "/file" ) )
                .build( );

        ResultActions resultActions = mvc.perform( request );

        String expectedJson = mapper.writeValueAsString( new SuccessResponse( true ) );

        resultActions.andExpect( status( ).isOk( ) ).andExpect( content( ).string( expectedJson ) );
    }

    @Test
    void deleteNonExistingFile( ) throws Exception {
        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.empty( ) );
        doNothing( ).when( fileRepository ).delete( any( ) );

        MockHttpServletRequestBuilder request = delete( "/file/{ID}", "id" );
        MockMvc mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
                .defaultRequest( get( "/file" ) )
                .build( );

        ResultActions resultActions = mvc.perform( request );

        String expectedJson = mapper.writeValueAsString( new ErrorResponse( "file not found" ) );

        resultActions.andExpect( status( ).isNotFound( ) ).andExpect( content( ).string( expectedJson ) );
    }

    @Test
    void addTagsWhenForExistingFile( ) throws Exception {
        File file = new File( "file", 123456789L );

        Set<String> tags = new HashSet<>( );
        tags.add( "tag12" );
        tags.add( "tag24" );

        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );

        String json = mapper.writeValueAsString( tags );

        MockHttpServletRequestBuilder request = post( "/file/{ID}/tags", "id" )
                .content( json )
                .contentType( MediaType.APPLICATION_JSON );
        MockMvc mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
                .defaultRequest( get( "/file" ) )
                .build( );

        ResultActions resultActions = mvc.perform( request );

        String expectedJson = mapper.writeValueAsString( new SuccessResponse( true ) );

        resultActions.andExpect( status( ).isOk( ) ).andExpect( content( ).string( expectedJson ) );
    }


    @Test
    void removeTags( ) throws Exception {
        File file = new File( "file", 123456789L );

        Set<String> tags = new HashSet<>( );
        tags.add( "tag12" );
        tags.add( "tag24" );
        file.setTags( tags );

        String json = mapper.writeValueAsString( tags );

        when( fileRepository.findById( any( ) ) ).thenReturn( Optional.of( file ) );

        MockHttpServletRequestBuilder request = delete( "/file/{ID}/tags", "id" )
                .content( json )
                .contentType( MediaType.APPLICATION_JSON );
        MockMvc mvc = MockMvcBuilders.webAppContextSetup( webApplicationContext )
                .defaultRequest( get( "/file" ) )
                .build( );

        ResultActions resultActions = mvc.perform( request );

        String expectedJson = mapper.writeValueAsString( new SuccessResponse( true ) );

        resultActions.andExpect( status( ).isOk( ) ).andExpect( content( ).string( expectedJson ) );
    }
}
