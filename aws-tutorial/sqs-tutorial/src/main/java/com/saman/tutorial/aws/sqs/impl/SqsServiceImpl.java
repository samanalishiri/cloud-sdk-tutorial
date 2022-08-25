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

package com.saman.tutorial.aws.sqs.impl;

import com.saman.tutorial.aws.sqs.service.SqsService;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.Optional;
import java.util.function.Function;

import static com.saman.tutorial.aws.sqs.utils.MessageConverter.convert;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class SqsServiceImpl implements SqsService {

    private final SqsClient client;

    public SqsServiceImpl(SqsClient client) {
        requireNonNull(client);

        this.client = client;
    }

    @Override
    public Optional<CreateQueueResponse> createQueue(String queueName) {
        requireNonNull(queueName);

        return ofNullable(client.createQueue(builder -> builder.queueName(queueName).build()));
    }

    @Override
    public Optional<GetQueueUrlResponse> getQueue(String queueName) {
        requireNonNull(queueName);

        return ofNullable(client.getQueueUrl(builder -> builder.queueName(queueName).build()));
    }

    @Override
    public <T> Optional<T> getQueue(String queueName, Function<GetQueueUrlResponse, T> then) {
        requireNonNull(queueName);
        requireNonNull(then);

        GetQueueUrlResponse queue = client.getQueueUrl(builder -> builder.queueName(queueName).build());

        if (isNull(queue))
            return Optional.empty();

        return ofNullable(then.apply(queue));
    }

    @Override
    public Optional<DeleteQueueResponse> deleteQueue(String queueName) {
        requireNonNull(queueName);

        return getQueue(queueName, queue -> client.deleteQueue(builder -> builder.queueUrl(queue.queueUrl()).build()));
    }

    @Override
    public Optional<SendMessageBatchResponse> sendMessages(String queueName, String... strings) {
        requireNonNull(queueName);
        requireNonNull(strings);

        return getQueue(queueName, queue -> client.sendMessageBatch(builder -> builder.queueUrl(queue.queueUrl())
                .entries(convert(strings))
                .build()));
    }

    @Override
    public Optional<ReceiveMessageResponse> receiveMessages(String queueName) {
        requireNonNull(queueName);

        return getQueue(queueName, queue -> client.receiveMessage(builder -> builder.queueUrl(queue.queueUrl())
                .build()));
    }
}
