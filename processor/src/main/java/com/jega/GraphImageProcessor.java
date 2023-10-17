package com.jega;

import com.jega.models.GraphImageProcessingResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.jboss.logging.Logger;

import java.util.Random;

@ApplicationScoped
public class GraphImageProcessor
{
    private static final Logger LOG = Logger.getLogger(GraphImageProcessor.class);

    private Random random = new Random();
    
    @Incoming("graph-image-requests")
    @Outgoing("graph-image-results")
    @Blocking
    public GraphImageProcessingResult process(String s3ImageId) throws InterruptedException {
        LOG.info("Got S3 Image Id ["+s3ImageId+"]");

        // simulate some hard-working task
        LOG.info("Hard Working...");
        Thread.sleep(3000);
        LOG.info("Work done.");

        return new GraphImageProcessingResult("s3ImageId: "+s3ImageId, "New Map()");
    }
}
