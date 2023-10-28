package com.jega;

import com.jega.models.FileObject;
import com.rabbitmq.client.ConnectionFactory;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(value = LocalStackS3TestResource.class)
@QuarkusTestResource(value = RabbitMQTestResource.class)
public class GraphImageProcessingControllerTest {

    @ConfigProperty(name = "rabbitmq-host")
    String rabbitMQHost;
    @ConfigProperty(name = "rabbitmq-port")
    String rabbitMQPort;
    @ConfigProperty(name = "rabbitmq-username")
    String rabbitMQUsername;
    @ConfigProperty(name = "rabbitmq-password")
    String rabbitMQPassword;

    @BeforeAll
    static void setUp() {

    }

    @Test
    public void listS3ObjectsEmpty() {
        List<FileObject> s3Objects = given()
                .when().get("/upload/list")
                .then()
                .statusCode(200)
                .extract().body().as(new TypeRef<>() {
                });
        assertTrue(s3Objects.isEmpty());
    }

    @Test
    public void uploadGraphImageToS3Bucket() throws IOException, URISyntaxException, TimeoutException {
        File imageFile = new File(getClass().getClassLoader().getResource("test-images/test-image.png").toURI());

        final byte[] bytes = IOUtils.toByteArray(imageFile.toURI());

        given()
                .multiPart("file", bytes)
                .formParam("fileName", imageFile.getName())
                .formParam("mimeType", "image/png")
                .when().post("/upload")
                .then()
                .statusCode(201);


        List<FileObject> s3Objects = given()
                .when().get("/upload/list")
                .then()
                .statusCode(200)
                .extract().body().as(new TypeRef<>() {
                });

        assertEquals(1, s3Objects.size());
        FileObject fileObject = s3Objects.get(0);
        assertEquals("test-image.png", fileObject.getObjectKey());
        assertEquals(33382, fileObject.getSize());
    }


    private void verifyMessages(String queue) {

    }

    private ConnectionFactory connectionFactory() {
        System.out.println(rabbitMQHost);
        System.out.println(rabbitMQPort);
        System.out.println(rabbitMQUsername);
        System.out.println(rabbitMQPassword);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(Integer.parseInt(rabbitMQPort));
        factory.setUsername(rabbitMQUsername);
        factory.setPassword(rabbitMQPassword);
        return factory;
    }

}