package org.jroots.queueing.client;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.jroots.queueing.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class RabbitProducer implements QueueProducer {

    private final String QUEUE_NAME = "test-queue";
    private final Logger logger = LoggerFactory.getLogger(RabbitProducer.class);

    @Override
    public Future<SendMessageResult> sendMessage(Message message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("docker.for.mac.localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.serializeToJson().getBytes());
            logger.info(" [x] Sent '{}'", message);
        } catch (TimeoutException | IOException e) {
            logger.error("Error {}, ", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
}
