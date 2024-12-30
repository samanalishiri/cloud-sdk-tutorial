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
import com.tutorial.aws.bucket.contract.object.ObjectServiceFacade;
import com.tutorial.aws.bucket.contract.object.ObjectSyncService;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Optional;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 */
public class ObjectServiceFacadeImpl implements ObjectServiceFacade {

    private final ObjectSyncService syncService;

    private final ObjectAsyncService asyncService;

    public ObjectServiceFacadeImpl(ObjectSyncService syncService, ObjectAsyncService asyncService) {
        requireNonNull(syncService);
        requireNonNull(asyncService);

        this.syncService = syncService;
        this.asyncService = asyncService;
    }

    @Override
    public Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object) {
        return syncService.putOneObject(bucketName, objectKey, object);
    }

    @Override
    public byte[] getOneObject(String bucketName, String objectKey) {
        return syncService.getOneObject(bucketName, objectKey);
    }

    @Override
    public Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey) {
        return syncService.deleteOneObject(bucketName, objectKey);
    }

    @Override
    public void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action) {
        asyncService.putOneObject(bucketName, objectKey, object, action);
    }

    @Override
    public void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action) {
        asyncService.getOneObject(bucketName, objectKey, action);
    }
}
