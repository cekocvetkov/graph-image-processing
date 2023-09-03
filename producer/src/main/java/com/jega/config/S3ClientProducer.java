package com.jega.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientProducer
{
    @Inject
    AWSConfig config;
    @Produces
    @ApplicationScoped
    public S3Client createS3Client() {
        S3Client s3 = S3Client.builder().credentialsProvider( () -> {
            return AwsBasicCredentials.create(
                    config.getAccessKey(),
                    config.getSecretAccessKey());
        } ).region( Region.of(config.getBucketRegion())).httpClient( UrlConnectionHttpClient.create() ).build();
        return s3;
    }
}
