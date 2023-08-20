package com.microservice.resourceservice.util;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
public class S3StorageServiceExtension implements BeforeAllCallback, AfterAllCallback {


    private static final String BUCKET_NAME = "resources";
    @Container
    private LocalStackContainer localStackContainer;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        localStackContainer = new LocalStackContainer(
                DockerImageName.parse("localstack/localstack:2.0.2"))
                        .withServices(S3);

        localStackContainer.start();
        localStackContainer.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);

        System.setProperty("aws.s3.endpoint", localStackContainer.getEndpointOverride(S3).toString());
        System.setProperty("aws.s3.credentials.access-key", localStackContainer.getAccessKey());
        System.setProperty("aws.s3.credentials.secret-key", localStackContainer.getSecretKey());
        System.setProperty("aws.credentials.region", localStackContainer.getRegion());
    }
}
