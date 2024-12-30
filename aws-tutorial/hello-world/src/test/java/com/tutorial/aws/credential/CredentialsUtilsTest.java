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

package com.tutorial.aws.credential;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishirishahrbabak
 */
@DisplayName("Credentials Tests")
class CredentialsUtilsTest {

    @Test
    void whenTryToLoadCredentialsByDefaultProvider_thenReturnCredentials() {
        var actual = CredentialsUtils.loadCredentials();

        assertTrue(actual.isPresent());
        actual.ifPresent(it -> {
            assertNotNull(it.accessKeyId());
            assertNotNull(it.secretAccessKey());
        });
    }
}
