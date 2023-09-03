package com.jega;

import com.jega.models.GraphImageProcessingResult;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.UUID;

@Path("/upload")
public class GraphImageProcessingController
{
    
    @Channel("graph-image-requests")
    Emitter<String> graphImageRequestEmitter;
    
    @Channel("graph-image-results")
    Multi<GraphImageProcessingResult> graphImageProcessingResults;
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createGraphImageProcessingRequest() {
        UUID uuid = UUID.randomUUID();
        graphImageRequestEmitter.send(uuid.toString());
        
        String toReturn = uuid+ ": "+" pending";
        return toReturn;
    }
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<GraphImageProcessingResult> stream() {
        return graphImageProcessingResults;
    }
}
