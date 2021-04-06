package org.jroots.queueing;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.jroots.queueing.api.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.RabbitMQContainer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TestSuite {

    private static DropwizardAppExtension<QueueGatewayConfiguration> extension = new DropwizardAppExtension<>(
            QueueGatewayApplication.class,
            ResourceHelpers.resourceFilePath("config.yml")
    );

    private RabbitMQContainer rabbitMQContainer;

    @BeforeEach
    public void setUp() {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.8.10");
        rabbitMQContainer.start();
    }

    @Test
    void testMessageSend() {
        Message message = new Message();
        message.setUUID("ID123");
        message.setContent("content");
        Client client = extension.client();

        Response response = client.target(
                String.format("http://localhost:%d/push-to-queue", extension.getLocalPort()))
                .request()
                .post(Entity.json(message));

        assert response.getStatus() == 204;
    }
}
