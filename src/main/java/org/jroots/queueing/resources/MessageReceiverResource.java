package org.jroots.queueing.resources;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.dropwizard.DropwizardExports;
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
    private final MetricRegistry metrics = new MetricRegistry();
    private final Counter counter = Counter.build().namespace("queue_cluster").name("gateway_messages").help("my counter").register();

    public MessageReceiverResource(QueueProducer queueProducer) {
        this.queueProducer = queueProducer;
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setThreadNamePrefix("sqsExecutor");
        executor.initialize();
        CollectorRegistry.defaultRegistry.register(new DropwizardExports(metrics));
    }

    @POST
    @Timed
    public void pushMessageToQueue(Message message) {
        executor.execute(() -> {
            counter.inc();
            queueProducer.sendMessage(message);
        });
    }
}
