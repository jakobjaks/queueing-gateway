package org.jroots.queueing;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.prometheus.client.exporter.HTTPServer;
import org.jroots.queueing.client.QueueProducer;
import org.jroots.queueing.client.SqsProducer;
import org.jroots.queueing.health.TemplateHealthCheck;
import org.jroots.queueing.resources.MessageReceiverResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class QueueGatewayApplication extends Application<QueueGatewayConfiguration> {

    private AnnotationConfigApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(QueueGatewayApplication.class);

    public static void main(final String[] args) throws Exception {
        new QueueGatewayApplication().run(args);
    }

    @Override
    public String getName() {
        return "QueueGateway";
    }

    @Override
    public void initialize(final Bootstrap<QueueGatewayConfiguration> bootstrap) {

    }

    @Override
    public void run(final QueueGatewayConfiguration configuration,
                    final Environment environment) {
        final QueueProducer queueProducer = new SqsProducer(configuration);
        final MessageReceiverResource resource = new MessageReceiverResource(queueProducer);
        environment.jersey().register(resource);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        try {
            new HTTPServer(9092);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
