package com.ah.filestorage.response;

import com.ah.filestorage.entity.File;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilePagingResponse {
    @JsonProperty("total")
    private long totalPages;

    private List<File> page;
}
