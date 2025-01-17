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

package com.tutorial.aws.bucket;

import com.tutorial.aws.bucket.implementation.*;
import com.tutorial.aws.bucket.contract.S3Facade;
import com.tutorial.aws.bucket.implementation.bucket.BucketServiceImpl;
import com.tutorial.aws.bucket.implementation.object.ObjectAsyncServiceImpl;
import com.tutorial.aws.bucket.implementation.object.ObjectServiceFacadeImpl;
import com.tutorial.aws.bucket.implementation.object.ObjectSyncServiceImpl;
import com.tutorial.aws.bucket.factory.S3ClientFactory;

/**
 * @author Saman Alishirishahrbabak
 */
public class TestServiceFactory {

    public static S3Facade createS3Facade(S3ClientFactory clientFactory) {
        var bucketService = new BucketServiceImpl(clientFactory.createS3Client(), clientFactory.getRegion());

        var objectSyncService = new ObjectSyncServiceImpl(clientFactory.createS3Client());
        var objectAsyncService = new ObjectAsyncServiceImpl(clientFactory.createAsyncS3Client());
        var objectServiceFacade = new ObjectServiceFacadeImpl(objectSyncService, objectAsyncService);

        return new S3FacadeImpl(bucketService, objectServiceFacade);
    }

}
