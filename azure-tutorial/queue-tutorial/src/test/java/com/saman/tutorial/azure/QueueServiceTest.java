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

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("Queue Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class QueueServiceTest {

    private static final QueueService underTest = new QueueService(TestEnv.loadAccountProperties());
    private final Logger logger = LoggerFactory.getLogger(QueueServiceTest.class.getSimpleName());

    @BeforeAll
    static void setUp() {
        assertNotNull(underTest);
    }

    @Test
    @DisplayName("create queue")
    @Order(1)
    void createQueue_WhenCreateQueue_ThenRunWithoutException() {
        assertDoesNotThrow(underTest::createQueue);
    }

    @Test
    @DisplayName("send message")
    @Order(2)
    void sendMessage_GivenMessage_WhenSendToQueue_ThenReturnMessageId() {
        var givenMessage = "Hello to Azure queue";

        var result = underTest.sendMessage(givenMessage);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("get message")
    @Order(3)
    void getMessage_WhenGetMessage_ThenReturnMessageItem() {
        var result = underTest.getMessage();

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("receive message")
    @Order(4)
    void receiveMessage_WhenReceiveMessage_ThenProcessReceivedMessages() {
        underTest.receiveMessage((client, message) -> {
            assertNotNull(message);
            assertNotNull(message.getMessageId());

            logger.info("message received, ID: {}, body: {}", message.getMessageId(), message.getBody().toString());
            client.deleteMessage(message.getMessageId(), message.getPopReceipt());
        });

    }

    @Test
    @DisplayName("delete queue")
    @Order(5)
    void deleteQueue_WhenDeleteQueue_ThenRunWithoutException() {
        assertDoesNotThrow(underTest::deleteQueue);
    }
}