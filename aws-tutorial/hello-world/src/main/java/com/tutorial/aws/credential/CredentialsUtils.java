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

package com.tutorial.aws.credential;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.util.Optional;

/**
 * @author Saman Alishirishahrbabak
 */
public final class CredentialsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsUtils.class.getSimpleName());

    private CredentialsUtils() {
    }

    /**
     * This method loads default credentials from installed AWS CLI.
     *
     * @return {@link Optional<AwsCredentials>}
     */
    public static Optional<AwsCredentials> loadCredentials() {
        LOGGER.info("Load credentials...");
        return Try.of(DefaultCredentialsProvider::create)
                .map(DefaultCredentialsProvider::resolveCredentials)
                .toJavaOptional();
    }
}
