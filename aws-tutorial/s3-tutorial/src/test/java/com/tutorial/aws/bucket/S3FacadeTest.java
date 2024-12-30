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

import com.tutorial.aws.bucket.contract.S3Facade;
import com.tutorial.aws.bucket.factory.S3ClientFactory;
import com.tutorial.aws.bucket.utils.IoUtils;
import io.vavr.control.Try;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saman Alishirishahrbabak
 */
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisabledIfSystemProperty(named = "credentials", matches = "unknown", disabledReason = "There is no real AWS account or any tools for simulating")
class S3FacadeTest {

    private static final String BUCKET_NAME = "saman-aws-tutorial-bucket";

    private static final String OBJECT_NAME = "saman-aws-tutorial-object";

    private static final String TEST_FILE = "test.txt";

    private static final String TEST_FILE_RESOURCES_PATH = "src/test/resources/%s";

    private static final String TEST_FILE_TARGET_PATH = "target/%s_%d";

    private static S3Facade systemUnderTest;

    @BeforeAll
    static void setUp() {
        systemUnderTest = Try.of(() -> TestServiceFactory.createS3Facade(new S3ClientFactory(TestEnv.loadCredentials())))
                .onFailure(Assertions::fail)
                .onSuccess(Assertions::assertNotNull)
                .get();
    }

    @Test
    @Order(1)
    void givenBucketName_whenCreate_thenItShouldBeWaitUntilGetOKStatus() {
        var givenName = BUCKET_NAME;

        var actual = systemUnderTest.createBucket(givenName);

        assertTrue(actual.isPresent());
        actual.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });
    }

    @Test
    @Order(2)
    void givenNoParam_whenGet_thenReturnAllBucketAsList() {
        var actual = systemUnderTest.getAllBuckets();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    @Order(3)
    void givenBucketName_whenGet_thenReturnBucket() {
        var givenName = BUCKET_NAME;

        var actual = systemUnderTest.getOneBucket(givenName);

        assertTrue(actual.isPresent());
        actual.ifPresent(it -> assertEquals(givenName, it.name()));
    }

    @Test
    @Order(4)
    void givenBucketNameAndObjectKeyAndFile_whenPutObject_thenReturnOKStatus() {
        var givenBucketName = BUCKET_NAME;
        var givenObjectKey = OBJECT_NAME;
        var givenFileName = TEST_FILE;

        var actual = systemUnderTest.putOneObject(givenBucketName, givenObjectKey, IoUtils.readFile(format(TEST_FILE_RESOURCES_PATH, givenFileName)));

        assertTrue(actual.isPresent());
        actual.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertNotNull(it.eTag());
        });
    }

    @Test
    @Order(5)
    void givenBucketNameAndObjectKeyAndFilePath_whenGetObject_thenReturnByteArray() {
        var givenBucketName = BUCKET_NAME;
        var givenObjectKey = OBJECT_NAME;
        var givenFileName = TEST_FILE;

        var actual = systemUnderTest.getOneObject(givenBucketName, givenObjectKey);
        assertNotNull(actual);
        IoUtils.createFile(
                format(TEST_FILE_TARGET_PATH, givenFileName, System.currentTimeMillis()),
                actual
        );
    }

    @Test
    @Order(6)
    void givenBucketNameAndObjectKey_whenDeleteObject_thenReturnOKStatus() {
        var givenBucketName = BUCKET_NAME;
        var givenObjectKey = OBJECT_NAME;

        var actual = systemUnderTest.deleteOneObject(givenBucketName, givenObjectKey);
        assertTrue(actual.isPresent());
        actual.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });

    }

    @Test
    @Order(7)
    void givenBucketNameAndObjectKeyAndFile_whenAsyncPutObject_thenReturnOKStatus() {
        var givenBucketName = BUCKET_NAME;
        var givenObjectKey = OBJECT_NAME;
        var givenFileName = TEST_FILE;

        systemUnderTest.putOneObject(
                givenBucketName,
                givenObjectKey,
                IoUtils.readFile(format(TEST_FILE_RESOURCES_PATH, givenFileName)),
                (response, err) -> {
                    assertNotNull(response);
                    assertTrue(response.sdkHttpResponse().isSuccessful());
                    assertEquals(200, response.sdkHttpResponse().statusCode());
                    assertNotNull(response.eTag());
                });
    }

    @Test
    @Order(8)
    void givenBucketNameAndObjectKeyAndFilePath_whenAsyncGetObject_thenReturnByteArray() {
        var givenBucketName = BUCKET_NAME;
        var givenObjectKey = OBJECT_NAME;
        var givenFileName = TEST_FILE;

        systemUnderTest.getOneObject(
                givenBucketName,
                givenObjectKey,
                (response, err) -> {
                    assertNotNull(response);
                    IoUtils.createFile(format(TEST_FILE_TARGET_PATH, givenFileName, System.currentTimeMillis()), response.asByteArray());
                    systemUnderTest.deleteOneObject(givenBucketName, givenObjectKey);
                });
    }

    @Test
    @Order(9)
    void givenBucketNameAsParam_whenDelete_thenReturnNoContentStatus() {
        var givenName = BUCKET_NAME;
        var actual = systemUnderTest.deleteOneBucket(givenName);
        assertTrue(actual.isPresent());
        actual.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(204, it.sdkHttpResponse().statusCode());
        });
    }
}
