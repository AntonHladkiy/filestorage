package com.ah.filestorage.contoller;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.response.FileIdResponse;
import com.ah.filestorage.response.SuccessResponse;
import com.ah.filestorage.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    private final FileService fileService;


    @PostMapping
    @ResponseBody
    public FileIdResponse saveFile(@RequestBody File file) {
        File savedFile = fileService.save(file);
        return new FileIdResponse(savedFile.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public SuccessResponse deleteFile(@PathVariable String id) {
        fileService.deleteById(id);
        return new SuccessResponse(true);
    }
}
