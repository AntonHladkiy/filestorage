package com.ah.filestorage.repository;

import com.ah.filestorage.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Set;

public interface FileRepository extends ElasticsearchRepository<File, String> {
    Page<File> findAllByNameContains(String namePart, Pageable pageable);

    Page<File> findAllByNameContainsAndTags(String namePart,Set<String> tag , Pageable pageable);

}