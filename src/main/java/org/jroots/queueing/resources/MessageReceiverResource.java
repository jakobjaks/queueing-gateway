package org.jroots.queueing.resources;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/push-to-queue")
@Produces(MediaType.APPLICATION_JSON)
public class MessageReceiverResource {

    @POST
    @Timed
    public void pushMessageToQueue() {
    }

}
