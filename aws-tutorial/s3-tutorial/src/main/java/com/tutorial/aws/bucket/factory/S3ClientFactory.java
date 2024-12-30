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

package com.tutorial.aws.bucket.factory;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class S3ClientFactory {

    public static final String ACCESS_KEY_PROPERTY = "accessKey";

    public static final String SECRET_KEY_PROPERTY = "secretKey";

    public static final String REGION_PROPERTY = "region";

    public static final String URL_PROPERTY = "url";

    public static final String DEFAULT_REGION = "us-west-2";

    private final Region region;

    private final URI uri;

    private final AwsCredentialsProvider credentialsProvider;

    public S3ClientFactory(Properties props) {
        requireNonNull(props);

        region = readRegion(props);
        uri = readUri(props);
        credentialsProvider = createCredentialsProvider(props);
    }

    private Region readRegion(Properties props) {
        return Region.of(props.getProperty(REGION_PROPERTY, DEFAULT_REGION));
    }

    private URI readUri(Properties props) {
        return props.containsKey(URL_PROPERTY) ? URI.create(props.getProperty(URL_PROPERTY)) : null;
    }

    private AwsCredentialsProvider createCredentialsProvider(Properties props) {
        var credentials = Credentials.readFrom(props);
        return credentials.isPresent()
                ? StaticCredentialsProvider.create(credentials.get())
                : DefaultCredentialsProvider.create();
    }

    public S3Client createS3Client() {
        var clientBuilder = S3Client.builder().region(region);
        if (Objects.nonNull(uri)) {
            clientBuilder.forcePathStyle(true).endpointOverride(uri);
        }
        clientBuilder.credentialsProvider(credentialsProvider);

        return clientBuilder.build();
    }

    public S3AsyncClient createAsyncS3Client() {
        var clientBuilder = S3AsyncClient.builder().region(region);
        if (Objects.nonNull(uri)) {
            clientBuilder.forcePathStyle(true).endpointOverride(uri);
        }
        clientBuilder.credentialsProvider(credentialsProvider);

        return clientBuilder.build();
    }

    public Region getRegion() {
        return region;
    }

    private record Credentials(String accessKey, String secretKey) implements AwsCredentials {

        @Override
        public String accessKeyId() {
            return accessKey;
        }

        @Override
        public String secretAccessKey() {
            return secretKey;
        }

        public static Optional<Credentials> readFrom(Properties props) {
            return (props.containsKey(ACCESS_KEY_PROPERTY) && props.containsKey(SECRET_KEY_PROPERTY))
                    ? Optional.of(new Credentials(props.getProperty(ACCESS_KEY_PROPERTY), props.getProperty(SECRET_KEY_PROPERTY)))
                    : Optional.empty();
        }
    }

}
