package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExtensionService {
    private final Map<String, String> extensionToTagMap = new HashMap<>( );
    private final String AUDIO = "audio";
    private final String DOCUMENT = "document";
    private final String VIDEO = "video";
    private final String IMAGE = "image";

    public ExtensionService( ) {
        extensionToTagMap.put( "mp3", AUDIO );
        extensionToTagMap.put( "docx", DOCUMENT );
        extensionToTagMap.put( "pdf", DOCUMENT );
        extensionToTagMap.put( "txt", DOCUMENT );
        extensionToTagMap.put( "mp4", VIDEO );
        extensionToTagMap.put( "png", IMAGE );
        extensionToTagMap.put( "gif", IMAGE );
        extensionToTagMap.put( "jpg", IMAGE );
    }

    public void addTag(File file) {
        String fileNameToLowerCase = file.getName( ).toLowerCase( );
        if (!fileNameToLowerCase.contains( "." )) {
            return;
        }
        String[] nameParts = fileNameToLowerCase.split( "\\." );
        String extension = nameParts[nameParts.length - 1];
        if (extensionToTagMap.containsKey( extension )) {
            file.getTags( ).add( extensionToTagMap.get( extension ) );
        }
    }
}
