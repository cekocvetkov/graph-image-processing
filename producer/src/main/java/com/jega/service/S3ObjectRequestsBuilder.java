package com.jega.service;

import com.jega.config.AWSConfig;
import com.jega.models.FormData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ApplicationScoped
public class S3ObjectRequestsBuilder
{
    @Inject
    AWSConfig config;
    
    public PutObjectRequest buildPutRequest( String fileName, String mimeType) {
        return PutObjectRequest.builder()
                .bucket(config.getBucketName())
                .key(fileName)
                .contentType(mimeType)
                .build();
    }
    
    public GetObjectRequest buildGetRequest(String objectKey) {
        return GetObjectRequest.builder()
                .bucket(config.getBucketName())
                .key(objectKey)
                .build();
    }
}
