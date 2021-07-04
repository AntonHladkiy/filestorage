package com.ah.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "file", createIndex = true)
public class File {

    public File(String name, Long size) {
        this.name = name;
        this.size = size;
        tags = new HashSet<>( );
    }

    @Id
    private String id;

    @NotNull(message = "Name cannot be empty")
    @Field(type = FieldType.Text)
    private String name;

    @Positive(message = "Size of file must be greater than 0")
    @NotNull(message = "There must be a size of a file")
    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Nested)
    private Set<String> tags;


}