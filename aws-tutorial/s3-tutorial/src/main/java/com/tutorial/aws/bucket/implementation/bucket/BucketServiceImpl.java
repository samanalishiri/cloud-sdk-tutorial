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

package com.tutorial.aws.bucket.implementation.bucket;

import com.tutorial.aws.bucket.contract.bucket.BucketService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.builder.SdkBuilder;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * @author Saman Alishirishahrbabak
 */
public final class BucketServiceImpl implements BucketService {

    private final S3Client client;

    private final Region region;

    public BucketServiceImpl(S3Client client, Region region) {
        requireNonNull(client);
        requireNonNull(region);

        this.client = client;
        this.region = region;
    }

    @Override
    public Optional<HeadBucketResponse> createBucket(String name) {
        requireNonNull(name);

        client.createBucket(CreateBucketRequest.builder()
                .bucket(name)
                .createBucketConfiguration(builder -> builder.locationConstraint(region.id()).build())
                .build());

        return client.waiter()
                .waitUntilBucketExists(HeadBucketRequest.builder().bucket(name).build())
                .matched()
                .response();
    }

    @Override
    public Optional<Bucket> getOneBucket(String name) {
        requireNonNull(name);

        return getAllBuckets()
                .stream()
                .filter(bucket -> bucket.name().equals(name))
                .findFirst();
    }

    @Override
    public Optional<DeleteBucketResponse> deleteOneBucket(String name) {
        requireNonNull(name);

        return ofNullable(client.deleteBucket(builder -> builder.bucket(name).build()));
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return client.listBuckets(SdkBuilder::build).buckets();
    }
}
