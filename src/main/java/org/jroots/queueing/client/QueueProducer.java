package org.jroots.queueing.client;

import org.jroots.queueing.api.Message;

public interface QueueProducer {
    void sendMessage(Message message);
}
