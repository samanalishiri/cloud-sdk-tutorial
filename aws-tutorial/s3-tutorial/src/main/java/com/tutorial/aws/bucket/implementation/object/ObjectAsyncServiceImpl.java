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

package com.tutorial.aws.bucket.implementation.object;

import com.tutorial.aws.bucket.contract.object.ObjectAsyncService;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;
import static software.amazon.awssdk.core.async.AsyncRequestBody.fromBytes;
import static software.amazon.awssdk.core.async.AsyncResponseTransformer.toBytes;

/**
 * @author Saman Alishirishahrbabak
 */
public class ObjectAsyncServiceImpl implements ObjectAsyncService {

    private final S3AsyncClient client;

    public ObjectAsyncServiceImpl(S3AsyncClient client) {
        requireNonNull(client);

        this.client = client;
    }

    @Override
    public void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);
        requireNonNull(action);

        client.putObject(builder -> builder.bucket(bucketName).key(objectKey).build(), fromBytes(object))
                .whenComplete(action)
                .join();
    }

    @Override
    public void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(action);

        client.getObject(builder -> builder.bucket(bucketName).key(objectKey).build(), toBytes())
                .whenComplete(action)
                .join();
    }
}
