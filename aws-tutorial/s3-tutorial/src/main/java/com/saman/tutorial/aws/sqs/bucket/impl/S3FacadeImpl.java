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

package com.saman.tutorial.aws.sqs.bucket.impl;

import com.saman.tutorial.aws.sqs.bucket.service.BucketObjectService;
import com.saman.tutorial.aws.sqs.bucket.service.BucketService;
import com.saman.tutorial.aws.sqs.bucket.service.S3Facade;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public final class S3FacadeImpl implements S3Facade {

    private final BucketService bucketService;

    private final BucketObjectService objectService;

    public S3FacadeImpl(BucketService bucketService, BucketObjectService objectService) {
        requireNonNull(bucketService);
        requireNonNull(objectService);

        this.bucketService = bucketService;
        this.objectService = objectService;
    }

    @Override
    public Optional<HeadBucketResponse> createBucket(String name) {
        requireNonNull(name);
        return bucketService.createBucket(name);
    }

    @Override
    public Optional<Bucket> getOneBucket(String name) {
        requireNonNull(name);
        return bucketService.getOneBucket(name);
    }

    @Override
    public Optional<DeleteBucketResponse> deleteOneBucket(String name) {
        requireNonNull(name);
        return bucketService.deleteOneBucket(name);
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return bucketService.getAllBuckets();
    }

    @Override
    public Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);

        return objectService.putOneObject(bucketName, objectKey, object);
    }

    @Override
    public byte[] getOneObject(String bucketName, String objectKey) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);

        return objectService.getOneObject(bucketName, objectKey);
    }

    @Override
    public Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);

        return objectService.deleteOneObject(bucketName, objectKey);
    }

    @Override
    public void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);

        objectService.putOneObject(bucketName, objectKey, object, action);
    }

    @Override
    public void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        objectService.getOneObject(bucketName, objectKey, action);
    }
}
