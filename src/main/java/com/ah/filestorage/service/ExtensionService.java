package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExtensionService {
    private final Map<String, String> extensionToTagMap = new HashMap<>( );

    public ExtensionService(){
        extensionToTagMap.put( "mp3", "audio" );
        extensionToTagMap.put( "docx", "document" );
        extensionToTagMap.put( "pdf", "document" );
        extensionToTagMap.put( "txt", "document" );
        extensionToTagMap.put( "mp4", "video" );
        extensionToTagMap.put( "png", "image" );
        extensionToTagMap.put( "gif", "image" );
        extensionToTagMap.put( "jpg", "image" );
    }
    public void addTag(File file) {
        String fileNameToLowerCase =  file.getName().toLowerCase();
        if (!fileNameToLowerCase.contains(".")){
            return;
        }
        String[] nameParts = fileNameToLowerCase.split("\\.");
        String extension = nameParts[nameParts.length - 1];
        if (extensionToTagMap.containsKey(extension)){
            file.getTags().add(extensionToTagMap.get(extension));
        }
    }
}
