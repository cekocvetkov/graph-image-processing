package com.jega;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

public class LocalStackS3TestResource implements QuarkusTestResourceLifecycleManager {
    private static final String BUCKET_NAME = "test-bucket";
    private static final String ACCESS_KEY = "test";
    private static final String SECRET_ACCESS_KEY = "test";
    private LocalStackContainer localStackContainer;

    @Override
    public Map<String, String> start() {
        localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:2.3.2"))
                .withServices(S3);

        localStackContainer.start();
        createBucket();

        Map<String, String> conf = setApplicationEnvVariables();
        return conf;
    }

    @Override
    public void stop() {
        localStackContainer.stop();
    }

    private void createBucket() {
        try {
            localStackContainer.execInContainer("awslocal", "s3api", "create-bucket", "--bucket", BUCKET_NAME);
            Container.ExecResult s3BucketsList = localStackContainer.execInContainer("awslocal", "s3api", "list-buckets");
            assert s3BucketsList.getStdout().contains("\"Name\": \"test-bucket\"");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Something went wrong while executing awslocal CLI commands in the localstack container", e);
        }
    }

    @NotNull
    private Map<String, String> setApplicationEnvVariables() {
        //Pass env variables to the application (Access and secret access key not really needed for Localstack)
        Map<String, String> conf = new HashMap<>();
        conf.put("aws.endpoint-override", String.valueOf(localStackContainer.getEndpointOverride(S3)));
        conf.put("aws.bucket-name", BUCKET_NAME);
        conf.put("aws.bucket-region", localStackContainer.getRegion());
        conf.put("aws.access-key", ACCESS_KEY);
        conf.put("aws.secret-access-key", SECRET_ACCESS_KEY);
        return conf;
    }
}
