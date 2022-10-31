/* **********************************************************************
 * Copyright (c) 2022 Saman Alishirishahrbabak.
 * All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ***********************************************************************/

package com.saman.tutorial.azure;


import com.azure.core.util.Context;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.PeekedMessageItem;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.SendMessageResult;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class QueueServiceImpl implements QueueService {
    private final Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class.getSimpleName());

    private final QueueClient client;

    public QueueServiceImpl(Properties properties) {
        requireNonNull(properties);

        var connectionString = properties.getProperty("connectionString");
        var queueName = properties.getProperty("queueName");

        client = new QueueClientBuilder()
                .connectionString(connectionString)
                .queueName(queueName)
                .buildClient();
    }

    @Override
    public void createQueue() {
        Try.run(client::create)
                .onSuccess(unused -> logger.info("queue {} was created", client.getQueueName()))
                .onFailure(ex -> logger.error("create queue {} failed, {}", client.getQueueName(), ex.getMessage()));
    }

    @Override
    public Optional<SendMessageResult> sendMessage(String message) {
        return Try.of(() -> client.sendMessage(message))
                .onSuccess(res -> logger.info("send message {} to queue {}",
                        res.getMessageId(),
                        client.getQueueName()))
                .onFailure(ex -> logger.error("send message to queue {} failed, {}",
                        client.getQueueName(),
                        ex.getMessage()))
                .toJavaOptional();
    }

    @Override
    public Optional<PeekedMessageItem> getMessage() {
        return Try.of(client::peekMessage)
                .onSuccess(res -> logger.info("get message {} from queue {}",
                        res.getMessageId(),
                        client.getQueueName()))
                .onFailure(ex -> logger.error("get message from queue {} failed, {}",
                        client.getQueueName(),
                        ex.getMessage()))
                .toJavaOptional();
    }

    @Override
    public void receiveMessage(BiConsumer<QueueClient, QueueMessageItem> process) {
        final int MAX_MESSAGES = 20;

        for (QueueMessageItem message : client.receiveMessages(MAX_MESSAGES,
                Duration.ofSeconds(300),
                Duration.ofSeconds(1),
                new Context("key1", "value1"))) {
            process.accept(client, message);
        }
    }

    @Override
    public void deleteQueue() {
        Try.run(client::deleteIfExists)
                .onSuccess(unused -> logger.info("queue {} was delete", client.getQueueName()))
                .onFailure(ex -> logger.error("delete queue {} failed, {}", client.getQueueName(), ex.getMessage()));
    }

}
