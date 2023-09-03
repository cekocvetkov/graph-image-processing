package com.jega;

import com.jega.models.GraphImageProcessingResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.reactive.messaging.annotations.Blocking;

import java.util.Random;

@ApplicationScoped
public class GraphImageProcessor
{
    
    private Random random = new Random();
    
    @Incoming("graph-image-requests")
    @Outgoing("graph-image-results")
    @Blocking
    public GraphImageProcessingResult process(String s3ImageId) throws InterruptedException {
        // simulate some hard-working task
        System.out.println("Hard Working");
        Thread.sleep(1000);
        System.out.println();
        return new GraphImageProcessingResult(s3ImageId, "New Map()");
    }
}
