package com.microservice.resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import com.amazonaws.services.s3.model.S3Object;
import com.microservice.resourceservice.config.AmazonS3Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Slf4j
public class S3StorageRepository {

    @Value("${aws.s3.bucket-name}") @Getter
    private String bucketName;
    private static final String FILE_EXTENSION = "fileExtension";
    private final AmazonS3 s3;

//

    public String upload(MultipartFile multipartFile) throws IOException {
        initializeBucket();
        String key = multipartFile.getOriginalFilename();
        s3.putObject(bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
        String filePath = "http://localhost:4566/" + bucketName + "/" + key;
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
        S3Object s3Object = s3.getObject(bucketName, id);
        String fileName = id + "." + s3Object.getObjectMetadata().getContentLength();
        Long contentLength = s3Object.getObjectMetadata().getContentLength();

        log.info("FileName: " + fileName);
        log.info("contentLength " + contentLength);
        s3.getObject(bucketName, id).getObjectContent();
        return s3Object.getObjectMetadata().toString();
    }

    private void initializeBucket() {
        if (!s3.doesBucketExistV2(bucketName)) {
            s3.createBucket(bucketName);
        }
    }
}
