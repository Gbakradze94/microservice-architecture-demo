package com.microservice.resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3StorageService {
    private static final String FILE_EXTENSION = "fileExtension";
    private final AmazonS3 s3;
    private final String bucketName;
    //private static final Logger LOGGER = LoggerFactory.getLogger(S3StorageService.class);

    @Autowired
    public S3StorageService(AmazonS3 s3, @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3 = s3;
        this.bucketName = bucketName;

        initializeBucket();
    }

    private void initializeBucket() {
        if (!s3.doesBucketExistV2(bucketName)) {
            s3.createBucket(bucketName);
        }
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String key = RandomStringUtils.randomAlphanumeric(50);
        s3.putObject(bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
        return key;
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        objectMetadata.getUserMetadata().put(FILE_EXTENSION, FilenameUtils.getExtension(file.getOriginalFilename()));

        return objectMetadata;
    }

}
