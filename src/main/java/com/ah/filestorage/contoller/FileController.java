package com.ah.filestorage.contoller;

import com.ah.filestorage.entity.File;
import com.ah.filestorage.exception.BadRequestException;
import com.ah.filestorage.response.FileIdResponse;
import com.ah.filestorage.response.SuccessResponse;
import com.ah.filestorage.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    private final FileService fileService;


    @PostMapping
    @ResponseBody
    public FileIdResponse saveFile(@RequestBody  @Valid File file, Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(errors.getFieldErrors().stream().map( DefaultMessageSourceResolvable::getDefaultMessage).collect( Collectors.joining(", ") ));
        }
        File savedFile = fileService.save(file);
        return new FileIdResponse(savedFile.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public SuccessResponse deleteFile(@PathVariable String id) {
        fileService.deleteById(id);
        return new SuccessResponse(true);
    }

    @PostMapping("/{id}/tags")
    @ResponseBody
    public SuccessResponse assignTags(@PathVariable String id,
                                     @RequestBody Set<String> tags) {
        fileService.addTagsById(id, tags);
        return new SuccessResponse(true);
    }

    @DeleteMapping("/{id}/tags")
    @ResponseBody
    public SuccessResponse removeTags(@PathVariable String id,
                                     @RequestBody Set<String> tags) {
        fileService.removeTagsById(id, tags);
        return new SuccessResponse(true);
    }
}
