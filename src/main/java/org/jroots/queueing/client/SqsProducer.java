package org.jroots.queueing.client;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.jroots.queueing.QueueGatewayConfiguration;
import org.jroots.queueing.api.Message;

public class SqsProducer implements QueueProducer {

    private final String sqsUrl;
    private final AmazonSQS amazonSQSClient;

    public SqsProducer(QueueGatewayConfiguration configuration) {
        sqsUrl = configuration.getSqsUrl();
        amazonSQSClient = AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();
    }

    @Override
    public void sendMessage(Message message) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(sqsUrl)
                .withMessageBody(message.serializeToJson())
                .withDelaySeconds(0);
        amazonSQSClient.sendMessage(request);
    }
}
