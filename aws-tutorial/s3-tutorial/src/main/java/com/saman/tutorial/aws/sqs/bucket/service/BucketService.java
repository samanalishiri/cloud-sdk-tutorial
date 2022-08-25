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

package com.saman.tutorial.aws.sqs.bucket.service;

import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public interface BucketService extends Service {

    /**
     * This method create a bucket by the S3 service and wait to get response.
     *
     * @param name name of bucket
     * @return {@link Optional<HeadBucketResponse>}
     */
    Optional<HeadBucketResponse> createBucket(String name);

    /**
     * This method get created bucket object from S3 service.
     *
     * @param name name of bucket
     * @return {@link Optional<Bucket>}
     */
    Optional<Bucket> getOneBucket(String name);

    /**
     * This method delete a bucket by {@code name}.
     *
     * @param name name of bucket
     * @return {@link Optional<DeleteBucketResponse>}
     */
    Optional<DeleteBucketResponse> deleteOneBucket(String name);

    /**
     * This method returns all buckets as a {@link List}.
     *
     * @return {@link List<Bucket>}
     */
    List<Bucket> getAllBuckets();
}
