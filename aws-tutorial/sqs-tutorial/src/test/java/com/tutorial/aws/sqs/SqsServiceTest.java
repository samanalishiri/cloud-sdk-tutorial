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

package com.tutorial.aws.sqs;

import com.tutorial.aws.sqs.service.SqsService;
import com.tutorial.aws.sqs.utils.SqsClientFactory;
import io.vavr.control.Try;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;

import java.util.Optional;

import static com.tutorial.aws.sqs.utils.IoUtils.createFile;
import static com.tutorial.aws.sqs.utils.MessageConverter.convert;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("SQS Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SqsServiceTest {

    private static SqsService underTest;

    @BeforeAll
    static void setUp() {
        underTest = Try.of(() -> TestServiceFactory.createS3Facade(new SqsClientFactory(TestEnv.loadCredentials())))
                .onFailure(Assertions::fail)
                .onSuccess(Assertions::assertNotNull)
                .get();
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-sqs"})
    @DisplayName("sqs create queue")
    @Order(1)
    void createQueue_GivenQueueNameAsParam_WhenSendCreateRequest_ThenReturnOKStatus(String queueName) {
        Optional<CreateQueueResponse> response = underTest.createQueue(queueName);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertNotNull(it.queueUrl());
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}'', message=''{1}''")
    @CsvSource(value = {"saman-aws-tutorial-sqs" + ",Hello", "saman-aws-tutorial-sqs" + ",I am a client", "saman-aws-tutorial-sqs" + ",Bye"})
    @DisplayName("send a message")
    @Order(2)
    void sendMessage_GivenQueueUrlAs1stAndMessageAs2dnParam_WhenSendRequest_ThenReturnOKStatus(String queueName, String message) {
        Optional<SendMessageBatchResponse> response = underTest.sendMessages(queueName, message);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-sqs", "saman-aws-tutorial-sqs", "saman-aws-tutorial-sqs"})
    @DisplayName("receive message")
    @Order(3)
    void receiveMessage_GivenQueueNameAsParam_WhenSendReceiveMessageRequest_ThenReturnOKStatusAndMessages(String queueName) {
        Optional<ReceiveMessageResponse> response = underTest.receiveMessages(queueName);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.hasMessages());
            createFile(format("target/%s_%d.txt", "temp-test-result", System.currentTimeMillis()), convert(it.messages()));
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-sqs"})
    @DisplayName("sqs delete queue")
    @Order(4)
    void deleteQueue_GivenQueueNameAsParam_WhenSendDeleteRequest_ThenReturnOKStatus(String queueName) {
        Optional<DeleteQueueResponse> response = underTest.deleteQueue(queueName);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });
    }
}
