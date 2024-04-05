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

package com.tutorial.aws.sqs.utils;

import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public final class MessageConverter {

    private MessageConverter() {
    }

    public static SendMessageBatchRequestEntry convert(String it) {
        return SendMessageBatchRequestEntry.builder()
                .id(UUID.randomUUID().toString())
                .messageBody(it)
                .build();
    }

    public static SendMessageBatchRequestEntry[] convert(String... strings) {
        return stream(strings).map(MessageConverter::convert).toArray(SendMessageBatchRequestEntry[]::new);
    }

    public static String[] convert(List<Message> messages) {
        return messages.stream()
                .map(Message::body)
                .toArray(String[]::new);
    }
}
