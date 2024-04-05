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

package com.tutorial.aws.sqs.service;

import software.amazon.awssdk.services.sqs.model.*;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public interface SqsService extends Service {

    /**
     * This method creates a queue by AWS SQS service.
     *
     * @param queueName name of queue
     * @return {@link Optional<CreateQueueResponse>}
     */
    Optional<CreateQueueResponse> createQueue(String queueName);

    /**
     * This method gets a queue object from AWS SQS service.
     *
     * @param queueName name of queue
     * @return {@link Optional<GetQueueUrlResponse>}
     */
    Optional<GetQueueUrlResponse> getQueue(String queueName);

    /**
     * This method gets a queue object from AWS SQS service.
     *
     * @param queueName name of queue
     * @param then      an action that execute after getting a queue object.
     * @param <T>       type of return object
     * @return {@link Optional<T>}
     */
    <T> Optional<T> getQueue(String queueName, Function<GetQueueUrlResponse, T> then);

    /**
     * This method deletes a queue fromAWS SQS service.
     *
     * @param queueName name of queue
     * @return {@link Optional<DeleteQueueResponse>}
     */
    Optional<DeleteQueueResponse> deleteQueue(String queueName);

    /**
     * This method sens some messages to a message queue.
     *
     * @param queueName name of queue
     * @param strings   messages
     * @return {@link Optional<SendMessageBatchResponse>}
     */
    Optional<SendMessageBatchResponse> sendMessages(String queueName, String... strings);

    /**
     * This method receive messages from a message queue.
     *
     * @param queueName name of queue
     * @return {@link Optional<ReceiveMessageResponse>}
     */
    Optional<ReceiveMessageResponse> receiveMessages(String queueName);
}
