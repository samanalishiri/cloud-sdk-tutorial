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

package com.tutorial.aws.bucket.contract.object;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.function.BiConsumer;

/**
 * @author Saman Alishirishahrbabak
 */
public interface ObjectAsyncService {

    /**
     * This method uses for upload an object in the bucket but, it does not block the thread.
     *
     * @param bucketName name of bucket
     * @param objectKey  key of object
     * @param object     the object to upload in the {@code bucketName}
     * @param action     an action that should be executed after a request is complete
     */
    void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action);

    /**
     * This method uses for download an object in the bucket but, it does not block the thread.
     *
     * @param bucketName name of bucket
     * @param objectKey  key of object
     * @param action     an action that should be executed after a request is complete
     */
    void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action);
}
