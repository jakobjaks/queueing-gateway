package org.jroots.queueing.resources;

import com.codahale.metrics.annotation.Timed;
import org.jroots.queueing.api.Message;
import org.jroots.queueing.client.QueueProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/push-to-queue")
@Produces(MediaType.APPLICATION_JSON)
public class MessageReceiverResource {

    private final QueueProducer queueProducer;
    private final Logger logger = LoggerFactory.getLogger(MessageReceiverResource.class);

    public MessageReceiverResource(QueueProducer queueProducer) {
        this.queueProducer = queueProducer;
    }

    @POST
    @Timed
    public void pushMessageToQueue(Message message) {
        queueProducer.sendMessage(message);
    }
}
