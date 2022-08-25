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

package com.saman.tutorial.aws.sqs.bucket;

import com.saman.tutorial.aws.sqs.bucket.impl.*;
import com.saman.tutorial.aws.sqs.bucket.service.S3Facade;
import com.saman.tutorial.aws.sqs.bucket.utils.S3ClientFactory;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class TestServiceFactory {

    public static S3Facade createS3Facade(S3ClientFactory clientFactory) {
        var bucketService = new BucketServiceImpl(clientFactory.getS3Client(), clientFactory.getRegion());

        var objectSyncService = new BucketObjectSyncServiceImpl(clientFactory.getS3Client());
        var objectAsyncService = new BucketObjectAsyncServiceImpl(clientFactory.getS3AsyncClient());
        var objectService = new BucketObjectServiceImpl(objectSyncService, objectAsyncService);

        return new S3FacadeImpl(bucketService, objectService);
    }

}
