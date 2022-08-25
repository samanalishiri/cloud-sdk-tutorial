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
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import io.vavr.control.Try;

import java.util.Locale;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public final class CredentialUtils {

    private CredentialUtils() {
    }

    public static Optional<BlobServiceClient> connectToBlobStorage(String accountName, String accountKey) {
        return Try.of(() -> new BlobServiceClientBuilder()
                .credential(new StorageSharedKeyCredential(accountName, accountKey))
                .endpoint(format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName))
                .buildClient()
        ).toJavaOptional();
    }
}