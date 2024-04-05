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
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("Credentials Tests")
class CredentialsUtilsTest {

    @Test
    @DisplayName("load default credentials via AWS CLI")
    void loadCredentials_GivenNoParam_WhenLoadDefaultCredentialsFromSdk_ThenItShouldBeLoadDefaultProfile() {
        Optional<AwsCredentials> credentials = CredentialsUtils.loadCredentials();
        assertTrue(credentials.isPresent());
        credentials.ifPresent(it -> {
            assertNotNull(it.accessKeyId());
            assertNotNull(it.secretAccessKey());
        });
    }
}
