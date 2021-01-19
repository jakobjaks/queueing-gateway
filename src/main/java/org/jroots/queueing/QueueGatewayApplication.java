package org.jroots.queueing;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jroots.queueing.health.TemplateHealthCheck;
import org.jroots.queueing.resources.MessageReceiverResource;

public class QueueGatewayApplication extends Application<QueueGatewayConfiguration> {

    public static void main(final String[] args) throws Exception {
        new QueueGatewayApplication().run(args);
    }

    @Override
    public String getName() {
        return "QueueGateway";
    }

    @Override
    public void initialize(final Bootstrap<QueueGatewayConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final QueueGatewayConfiguration configuration,
                    final Environment environment) {
        final MessageReceiverResource resource = new MessageReceiverResource();
        environment.jersey().register(resource);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }

}
