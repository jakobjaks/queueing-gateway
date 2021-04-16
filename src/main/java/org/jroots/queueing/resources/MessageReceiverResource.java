package org.jroots.queueing.resources;

import com.codahale.metrics.annotation.Timed;
import org.jroots.queueing.api.Message;
import org.jroots.queueing.client.QueueProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/push-to-queue")
@Produces(MediaType.APPLICATION_JSON)
public class MessageReceiverResource {

    private final QueueProducer queueProducer;
    private final Logger logger = LoggerFactory.getLogger(MessageReceiverResource.class);
    private final ThreadPoolTaskExecutor executor;

    public MessageReceiverResource(QueueProducer queueProducer) {
        this.queueProducer = queueProducer;
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setThreadNamePrefix("sqsExecutor");
        executor.initialize();
    }

    @POST
    @Timed
    public void pushMessageToQueue(Message message) {
        executor.execute(() -> queueProducer.sendMessage(message));
    }
}
