package com.microservice.resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import com.amazonaws.services.s3.model.S3Object;
import com.microservice.resourceservice.config.AmazonS3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {
    private static final String FILE_EXTENSION = "fileExtension";
    private final AmazonS3 s3;
    private final AmazonS3Config s3ClientConfig;


    @PostConstruct
    private void initializeBucket() {
        if (!s3.doesBucketExistV2(s3ClientConfig.getBucketName())) {
            s3.createBucket(s3ClientConfig.getBucketName());
        }
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String key = multipartFile.getOriginalFilename();
        s3.putObject(s3ClientConfig.getBucketName(), key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
        String filePath = "http://localhost:4566/" + s3ClientConfig.getBucketName() + "/" + key;
        return filePath;
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        objectMetadata.getUserMetadata().put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));

        return objectMetadata;
    }

    public String download(String id) {
        S3Object s3Object = s3.getObject(s3ClientConfig.getBucketName(), id);
        String fileName = id + "." + s3Object.getObjectMetadata().getContentLength();
        Long contentLength = s3Object.getObjectMetadata().getContentLength();

        log.info("FileName: " + fileName);
        log.info("contentLength " + contentLength);
        s3.getObject(s3ClientConfig.getBucketName(), id).getObjectContent();
        return s3Object.getObjectMetadata().toString();
    }
}
