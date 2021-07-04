package com.ah.filestorage.service;

import com.ah.filestorage.entity.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExtensionServiceTest {
    @InjectMocks
    private final ExtensionService extensionService=new ExtensionService();

    private final Map<String, String> map = new HashMap<>();

    @BeforeEach
    public void setUp() {
        map.put("pdf", "document");
        map.put("gif", "image");
        ReflectionTestUtils.setField(extensionService, "extensionToTagMap", map);
    }

    @Test
    void fileThatShouldGetTag(){
        File file = new File("test1.pdf", 123456789L);
        extensionService.addTag(file);
        assertTrue(file.getTags().contains(map.get("pdf")));

        file = new File("test2.gif", 123456789L);
        extensionService.addTag(file);
        assertTrue(file.getTags().contains(map.get("gif")));

        file = new File("TEST3.PDF", 123456789L);
        extensionService.addTag(file);
        assertTrue(file.getTags().contains(map.get("pdf")));

    }

    @Test
    void fileThatShouldNotGetTag(){
        File file = new File("Wrong.Extension", 123456789L);
        extensionService.addTag(file);
        assertTrue(file.getTags().isEmpty());

        file = new File("noExtension", 123456789L);
        extensionService.addTag(file);
        assertTrue(file.getTags().isEmpty());
    }
}
