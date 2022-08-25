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

package com.saman.tutorial.aws.sqs.bucket.utils;

import io.vavr.control.Try;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

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
        S3ClientBuilder builder = S3Client.builder();

        builder.region(region);

        if (properties.containsKey("url"))
            builder.endpointOverride(URI.create(String.valueOf(properties.get("url"))));

        if (properties.containsKey("accessKey") && properties.containsKey("secretKey"))
            builder.credentialsProvider(StaticCredentialsProvider.create(new CredentialsImpl(
                    properties.getProperty("accessKey"),
                    properties.getProperty("secretKey")
            )));
        else
            Try.of(() -> builder.credentialsProvider(DefaultCredentialsProvider.create()));

        return builder.build();
    }

    private S3AsyncClient createAsyncClient(Properties properties) {
        S3AsyncClientBuilder builder = S3AsyncClient.builder();

        builder.region(region);

        if (properties.containsKey("url"))
            builder.endpointOverride(URI.create(String.valueOf(properties.get("url"))));

        if (properties.containsKey("accessKey") && properties.containsKey("secretKey"))
            builder.credentialsProvider(StaticCredentialsProvider.create(new CredentialsImpl(
                    properties.getProperty("accessKey"),
                    properties.getProperty("secretKey")
            )));
        else
            Try.of(() -> builder.credentialsProvider(DefaultCredentialsProvider.create()));

        return builder.build();
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

    private static class CredentialsImpl implements AwsCredentials {

        private String accessKey;

        private String secretKey;

        public CredentialsImpl(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }

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
