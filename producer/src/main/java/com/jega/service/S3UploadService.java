package com.jega.service;

import com.jega.config.AWSConfig;
import com.jega.models.FileObject;
import com.jega.models.FormData;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class S3UploadService
{
    AWSConfig config;
    S3Client s3;
    S3ObjectRequestsBuilder requestsBuilder;

    public PutObjectResponse uploadFileToS3(FormData formData) {
        PutObjectResponse putResponse = s3.putObject(requestsBuilder.buildPutRequest(formData.getFileName(), formData.getMimeType()),
                RequestBody.fromFile(formData.getData()));


        return putResponse;
    }

    public List<FileObject> getS3ObjectsList () {
        ListObjectsRequest listRequest = ListObjectsRequest.builder().bucket(config.getBucketName()).build();

        //HEAD S3 objects to get metadata
        return s3.listObjects(listRequest).contents().stream()
                .map( FileObject::from)
                .sorted( Comparator.comparing(FileObject::getObjectKey))
                .collect( Collectors.toList());
    }

}
