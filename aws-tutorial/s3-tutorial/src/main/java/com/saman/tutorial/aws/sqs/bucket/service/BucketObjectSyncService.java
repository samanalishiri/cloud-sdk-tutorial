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

import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Optional;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public interface BucketObjectSyncService extends Service {

    /**
     * This method use for upload an object to a bucket ({@code bucketName}).
     *
     * @param bucketName name of bucket
     * @param objectKey  key of object
     * @param object     the object to upload in the {@code bucketName}
     * @return {@link Optional<PutObjectResponse>}
     */
    Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object);

    /**
     * This method use for download an object in the bucket ({@code bucketName}).
     *
     * @param bucketName name of bucket
     * @param objectKey  key of object
     * @return {@code byte[]}
     */
    byte[] getOneObject(String bucketName, String objectKey);

    /**
     * This method use for delete an object in the bucket ({@code bucketName}).
     *
     * @param bucketName name of bucket
     * @param objectKey  key of object
     * @return {@link Optional<DeleteObjectsResponse>}
     */
    Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey);

}
