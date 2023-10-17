package com.jega.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

public class S3ClientProducer {
    @Inject
    AWSConfig config;

    @Produces
    @ApplicationScoped
    public S3Client createS3Client() throws URISyntaxException {
        // forcePathStyle important for localstack in this case: https://docs.localstack.cloud/user-guide/aws/s3/#path-style-and-virtual-hosted-style-requests
        S3Client s3 = S3Client.builder().forcePathStyle(true).credentialsProvider(() -> {
            return AwsBasicCredentials.create(
                    config.getAccessKey(),
                    config.getSecretAccessKey());
        }).region(Region.of(config.getBucketRegion())).endpointOverride(new URI(config.getEndpointOverride())).httpClient(UrlConnectionHttpClient.create()).build();
        return s3;
    }
}
