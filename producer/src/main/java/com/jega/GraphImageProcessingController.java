package com.jega;

import com.jega.config.AWSConfig;
import com.jega.models.FileObject;
import com.jega.models.FormData;
import com.jega.models.GraphImageProcessingResult;
import com.jega.service.S3UploadService;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;

@Path("/upload")
public class GraphImageProcessingController
{
    
    @Channel("graph-image-requests")
    Emitter<String> graphImageRequestEmitter;
    
    @Channel("graph-image-results")
    Multi<GraphImageProcessingResult> graphImageProcessingResults;
    
    @Inject
    S3UploadService s3UploadService;
    
    @Inject
    AWSConfig config;
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile( FormData formData) {
        if (formData.getFileName() == null || formData.getFileName().isEmpty()) {
            return Response.status( Response.Status.BAD_REQUEST).build();
        }
    
        if (formData.getMimeType() == null || formData.getMimeType().isEmpty()) {
            return Response.status( Response.Status.BAD_REQUEST).build();
        }
                PutObjectResponse putResponse = s3UploadService.uploadFileToS3( formData );

        if (putResponse != null) {
            return Response.ok().status( Response.Status.CREATED).build();
        } else {
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileObject> listFiles() {
        return s3UploadService.getS3ObjectsList();
    }
    
//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    public String createGraphImageProcessingRequest() {
//        UUID uuid = UUID.randomUUID();
//        graphImageRequestEmitter.send(uuid.toString());
//
//        String toReturn = uuid+ ": "+" pending";
//        return toReturn;
//    }
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<GraphImageProcessingResult> stream() {
        return graphImageProcessingResults;
    }
}
