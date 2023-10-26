package com.jega;

import com.jega.models.FileObject;
import com.jega.models.FormData;
import com.jega.models.GraphImageProcessingResult;
import com.jega.service.GraphImageProcessorService;
import com.jega.service.S3Service;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/upload")
public class GraphImageProcessingController {
    private static final Logger LOG = Logger.getLogger(GraphImageProcessingController.class);

    @Channel("graph-image-results")
    Multi<GraphImageProcessingResult> graphImageProcessingResults;

    GraphImageProcessorService graphImageProcessorService;

    S3Service s3Service;

    public GraphImageProcessingController(GraphImageProcessorService graphImageProcessorService, S3Service s3Service) {
        this.graphImageProcessorService = graphImageProcessorService;
        this.s3Service = s3Service;
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response processGraphImage(FormData formData) {
        graphImageProcessorService.processGraphImage(formData);
        return Response.status(Response.Status.CREATED).build();

    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileObject> listFiles() {
        LOG.info("Listing S3 object files");
        return s3Service.getS3ObjectsList();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<GraphImageProcessingResult> stream() {
        LOG.info("Graph Image Processing Result from graph-image-results queue");
        return graphImageProcessingResults;
    }
}
