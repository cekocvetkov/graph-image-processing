package com.jega.service;

import com.jega.config.AWSConfig;
import com.jega.models.FormData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@ApplicationScoped
public class GraphImageProcessorService {
    private static final Logger LOG = Logger.getLogger(GraphImageProcessorService.class);

    @Channel("graph-image-requests")
    Emitter<String> graphImageRequestEmitter;
    S3Service s3UploadService;

    AWSConfig config;

    public GraphImageProcessorService(S3Service s3Service, AWSConfig config) {
        this.s3UploadService = s3Service;
        this.config = config;
    }


    public void processGraphImage(FormData formData) {
        LOG.info("Start uploading file ...");

        validateData(formData);

        PutObjectResponse putResponse = s3UploadService.uploadFileToS3(formData);

        if (putResponse == null) {
            throw new ServerErrorException("Problems while uploading to S3 bucket", Response.Status.INTERNAL_SERVER_ERROR);
        }

        graphImageRequestEmitter.send(formData.getFileName());
    }

    private void validateData(FormData formData) {
        if (formData.getFileName() == null || formData.getFileName().isEmpty()) {
            throw new BadRequestException("No file name in request");
        }
        if (formData.getMimeType() == null || formData.getMimeType().isEmpty()) {
            throw new BadRequestException("No mime type in request");
        }
    }

}
