package org.jroots.queueing.client;

import com.amazonaws.services.sqs.model.SendMessageResult;
import org.jroots.queueing.api.Message;

import java.util.concurrent.Future;

public interface QueueProducer {
    Future<SendMessageResult> sendMessage(Message message);
}
