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

package com.saman.tutorial.aws.sqs;

import com.saman.tutorial.aws.sqs.impl.SqsServiceImpl;
import com.saman.tutorial.aws.sqs.service.SqsService;
import com.saman.tutorial.aws.sqs.utils.SqsClientFactory;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class TestServiceFactory {

    public static SqsService createS3Facade(SqsClientFactory clientFactory) {
        return new SqsServiceImpl(clientFactory.getClient());
    }
}
