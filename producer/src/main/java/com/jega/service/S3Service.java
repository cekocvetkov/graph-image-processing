package com.jega.service;

import com.jega.config.AWSConfig;
import com.jega.models.FileObject;
import com.jega.models.FormData;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class S3Service {
    private static final Logger LOG = Logger.getLogger(S3Service.class);
    private AWSConfig config;
    private S3Client s3;
    private S3ObjectRequestsBuilder requestsBuilder;

    public S3Service(AWSConfig config, S3Client s3, S3ObjectRequestsBuilder requestsBuilder) {
        this.config = config;
        this.s3 = s3;
        this.requestsBuilder = requestsBuilder;
    }

    public PutObjectResponse uploadFileToS3(FormData formData) {
        LOG.info("Saving image in s3 bucket...");
        LOG.info(RequestBody.fromFile(formData.getData()));
        PutObjectResponse putResponse = s3.putObject(requestsBuilder.buildPutRequest(formData.getFileName(), formData.getMimeType()),
                RequestBody.fromFile(formData.getData()));

        return putResponse;
    }

    public List<FileObject> getS3ObjectsList() {
        ListObjectsRequest listRequest = ListObjectsRequest.builder().bucket(config.getBucketName()).build();

        //HEAD S3 objects to get metadata
        return s3.listObjects(listRequest).contents().stream()
                .map(FileObject::from)
                .sorted(Comparator.comparing(FileObject::getObjectKey))
                .collect(Collectors.toList());
    }

}
