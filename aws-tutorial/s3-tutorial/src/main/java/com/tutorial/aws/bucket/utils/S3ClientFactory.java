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

package com.tutorial.aws.bucket.utils;

import io.vavr.control.Try;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public final class S3ClientFactory {

    private final Region region;

    private final S3Client s3Client;

    private final S3AsyncClient s3AsyncClient;

    public S3ClientFactory(Properties properties) {
        requireNonNull(properties);
        region = Region.of(String.valueOf(properties.getOrDefault("region", "us-west-2")));
        s3Client = createClient(properties);
        s3AsyncClient = createAsyncClient(properties);
    }

    private S3Client createClient(Properties properties) {
        var builder = S3Client.builder().region(region);
        if (properties.containsKey("url")) {
            builder.forcePathStyle(true).endpointOverride(URI.create(String.valueOf(properties.getProperty("url"))));
        }
        builder.credentialsProvider(createCredential(properties));

        return builder.build();
    }

    private S3AsyncClient createAsyncClient(Properties properties) {
        var builder = S3AsyncClient.builder().region(region);
        if (properties.containsKey("url")) {
            builder.forcePathStyle(true).endpointOverride(URI.create(String.valueOf(properties.getProperty("url"))));
        }
        builder.credentialsProvider(createCredential(properties));

        return builder.build();
    }

    private AwsCredentialsProvider createCredential(Properties properties) {
        if (properties.containsKey("accessKey") && properties.containsKey("secretKey")) {
            var accessKey = properties.getProperty("accessKey");
            var secretKey = properties.getProperty("secretKey");
            return StaticCredentialsProvider.create(new CredentialsImpl(accessKey, secretKey));
        } else {
            return Try.of(DefaultCredentialsProvider::create).getOrElseThrow(() -> new RuntimeException());
        }
    }

    public S3Client getS3Client() {
        return s3Client;
    }

    public S3AsyncClient getS3AsyncClient() {
        return s3AsyncClient;
    }

    public Region getRegion() {
        return region;
    }

    private record CredentialsImpl(String accessKey, String secretKey) implements AwsCredentials {

        @Override
        public String accessKeyId() {
            return accessKey;
        }

        @Override
        public String secretAccessKey() {
            return secretKey;
        }
    }

}
