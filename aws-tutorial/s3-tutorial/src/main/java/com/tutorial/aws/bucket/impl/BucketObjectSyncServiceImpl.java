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

package com.tutorial.aws.bucket.impl;

import com.tutorial.aws.bucket.service.BucketObjectSyncService;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class BucketObjectSyncServiceImpl implements BucketObjectSyncService {

    private final S3Client client;

    public BucketObjectSyncServiceImpl(S3Client client) {
        requireNonNull(client);

        this.client = client;
    }

    @Override
    public Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);

        return ofNullable(
                client.putObject(builder -> builder.bucket(bucketName).key(objectKey).build(), fromBytes(object))
        );
    }

    @Override
    public byte[] getOneObject(String bucketName, String objectKey) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);

        return client.getObjectAsBytes(builder -> builder.key(objectKey).bucket(bucketName).build()).asByteArray();
    }

    @Override
    public Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey) {

        return ofNullable(client.deleteObjects(requestBuilder -> requestBuilder
                .bucket(bucketName)
                .delete(builder ->
                        builder.objects(singletonList(ObjectIdentifier.builder().key(objectKey).build())).build())
                .build()));
    }
}
