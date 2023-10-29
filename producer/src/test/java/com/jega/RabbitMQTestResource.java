package com.jega;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

public class RabbitMQTestResource implements QuarkusTestResourceLifecycleManager {
    private RabbitMQContainer rabbitMQContainer;

    @Override
    public Map<String, String> start() {
        rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.12.7-management-alpine"));
        rabbitMQContainer.start();
        Map<String, String> conf = setApplicationEnvVariables();
        return conf;
    }

    @Override
    public void stop() {
        rabbitMQContainer.stop();
    }

    @NotNull
    private Map<String, String> setApplicationEnvVariables() {
        Map<String, String> conf = new HashMap<>();
        conf.put("rabbitmq-host", rabbitMQContainer.getHost());
        conf.put("rabbitmq-port", rabbitMQContainer.getAmqpPort().toString());
        conf.put("rabbitmq-username", rabbitMQContainer.getAdminUsername());
        conf.put("rabbitmq-password", rabbitMQContainer.getAdminPassword());
        return conf;
    }
}
