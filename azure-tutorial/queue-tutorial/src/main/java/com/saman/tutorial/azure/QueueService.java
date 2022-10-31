package com.saman.tutorial.azure;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.models.PeekedMessageItem;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.SendMessageResult;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface QueueService {
    void createQueue();

    Optional<SendMessageResult> sendMessage(String message);

    Optional<PeekedMessageItem> getMessage();

    void receiveMessage(BiConsumer<QueueClient, QueueMessageItem> process);

    void deleteQueue();
}
