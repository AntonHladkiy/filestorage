package com.ah.filestorage.repository;

import com.ah.filestorage.entity.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FileRepository extends ElasticsearchRepository<File, String> {

}