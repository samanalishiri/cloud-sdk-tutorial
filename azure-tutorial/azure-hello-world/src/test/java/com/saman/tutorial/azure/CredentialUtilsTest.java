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

import com.azure.storage.blob.BlobServiceClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.saman.tutorial.azure.CredentialUtils.connectToBlobStorage;
import static java.lang.System.getenv;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("Credential Utils Tests")
class CredentialUtilsTest {

    @Test
    @DisplayName("connect to blob storage with true data")
    void ConnectToBlobStorage_GivenAccountNameANdKey_WhenSendRequestToStorageService_ThenReturnBlobClient() {
        var givenAccountName = getenv("ACCOUNT_NAME");
        var givenAccountKey = getenv("ACCOUNT_KEY");

        Optional<BlobServiceClient> result = connectToBlobStorage(givenAccountName, givenAccountKey);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("get fail for connecting to blob storage with false data")
    void ConnectToBlobStorage_GivenWrongAccountNameOrKey_WhenSendRequestToStorageService_ThenReturnEmpty() {
        var givenAccountName = "invalid account name";
        var givenAccountKey = "invalid account key";

        Optional<BlobServiceClient> result = connectToBlobStorage(givenAccountName, givenAccountKey);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}