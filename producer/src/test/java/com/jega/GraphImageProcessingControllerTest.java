package com.jega;

import com.jega.models.FileObject;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(value = LocalStackS3TestResource.class)
public class GraphImageProcessingControllerTest {

    static {
//        RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.12.7-management-alpine"));


//        rabbitMQContainer.start();
    }


    @BeforeAll
    static void setUp() {

    }

    @Test
    public void listS3ObjectsEmpty() {
        List<FileObject> s3Objects = given()
                .when().get("/upload/list")
                .then()
                .statusCode(200)
                .extract().body().as(new TypeRef<List<FileObject>>() {
                });
        assertTrue(s3Objects.isEmpty());
    }

    @Test
    public void uploadGraphImageToS3Bucket() {
//        given()
//                .multiPart();
//                .when().get("/upload")
//                .then()
//                .statusCode(201);
//
//        assertTrue(s3Objects.isEmpty());
    }

}