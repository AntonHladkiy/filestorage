package com.ah.filestorage.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Document(indexName = "file")
public class File {

    @Id
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "There must be a name")
    @Field(type = FieldType.Text)
    private String name;

    @Positive(message = "Size of file must be greater than 0")
    @NotNull(message = "There must be a size of a file")
    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Text)
    private Set<String> tags;

    public File(String name, Long size) {
        this.name = name;
        this.size = size;
        tags = new HashSet<>();
    }

}