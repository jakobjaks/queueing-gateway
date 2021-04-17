package org.jroots.queueing.client;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.jroots.queueing.QueueGatewayConfiguration;
import org.jroots.queueing.api.Message;

import java.util.concurrent.Future;

public class SqsProducer implements QueueProducer {

    private final String sqsUrl;
    private final AmazonSQSAsync amazonSQSClient;

    public SqsProducer(QueueGatewayConfiguration configuration) {
        sqsUrl = configuration.getSqsUrl();
        amazonSQSClient = AmazonSQSAsyncClient.asyncBuilder().withRegion("us-east-1").build();
    }

    @Override
    public Future<SendMessageResult> sendMessage(Message message) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(sqsUrl)
                .withMessageBody(message.serializeToJson())
                .withDelaySeconds(0);
        return amazonSQSClient.sendMessageAsync(request);
    }
}
