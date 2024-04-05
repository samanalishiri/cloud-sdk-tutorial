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

import com.tutorial.aws.bucket.service.S3Facade;
import com.tutorial.aws.bucket.utils.IoUtils;
import com.tutorial.aws.bucket.utils.S3ClientFactory;
import io.vavr.control.Try;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("Bucket Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class S3FacadeTest {

    private static S3Facade underTest;

    @BeforeAll
    static void setUp() {
        underTest = Try.of(() -> TestServiceFactory.createS3Facade(new S3ClientFactory(TestEnv.loadCredentials())))
                .onFailure(Assertions::fail)
                .onSuccess(Assertions::assertNotNull)
                .get();
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-bucket"})
    @DisplayName("bucket sync creation")
    @Order(1)
    void createBucket_GivenBucketNameAsParam_WhenSendCreateRequest_ThenItShouldBeWaitUntilGetOKStatus(String name) {
        Optional<HeadBucketResponse> response = underTest.createBucket(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });
    }

    @Test
    @DisplayName("get all buckets")
    @Order(2)
    void getAllBuckets_GivenNoParam_WhenSendGetRequest_ThenReturnAllBucketAsList() {
        List<Bucket> buckets = underTest.getAllBuckets();
        assertNotNull(buckets);
        assertFalse(buckets.isEmpty());
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-bucket"})
    @DisplayName("get one bucket")
    @Order(3)
    void getOneBucket_GivenBucketNameAsParam_WhenSendGetRequest_ThenReturnTheBucket(String name) {
        Optional<Bucket> bucket = underTest.getOneBucket(name);
        assertTrue(bucket.isPresent());
        bucket.ifPresent(it -> assertEquals(name, it.name()));
    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', fileName=''{2}''")
    @CsvSource({"saman-aws-tutorial-bucket, aws-object, test-file.txt"})
    @DisplayName("put one object")
    @Order(4)
    void putOneObject_GivenBucketNameAndObjectKeyAndFileAsParam_WhenSendPutObjectRequest_ThenReturnTheOKStatus(
            String bucketName, String objectKey, String fileName) {

        Optional<PutObjectResponse> response = underTest.putOneObject(bucketName, objectKey,
                IoUtils.readFile(format("src/test/resources/%s", fileName)));
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertNotNull(it.eTag());
        });
    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', filePath=''{2}''")
    @CsvSource({"saman-aws-tutorial-bucket, aws-object, temp-test-result"})
    @DisplayName("get one object")
    @Order(5)
    void getOneObject_GivenBucketNameAndObjectKeyAndFilePathAsParam_WhenSendGetObjectRequest_ThenReturnTheByteArray(
            String bucketName, String objectKey, String filePath) {

        byte[] object = underTest.getOneObject(bucketName, objectKey);
        assertNotNull(object);
        IoUtils.createFile(format("target/%s_%d.txt", filePath, System.currentTimeMillis()), object);
    }


    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}''")
    @CsvSource({"saman-aws-tutorial-bucket, aws-object"})
    @DisplayName("delete one object")
    @Order(6)
    void deleteOneObject_GivenBucketNameAs1stAndObjectKeyAs2ndParam_WhenSendDeleteObjectRequest_ThenReturnTheOKStatus(
            String bucketName, String objectKey) {

        Optional<DeleteObjectsResponse> response = underTest.deleteOneObject(bucketName, objectKey);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
        });

    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', fileName=''{2}''")
    @CsvSource({"saman-aws-tutorial-bucket, aws-object, test-file.txt"})
    @DisplayName("put one object async")
    @Order(7)
    void putOneObject_GivenBucketNameAndObjectKeyAndFileAsParam_WhenSendAsyncPutObjectRequest_ThenReturnTheOKStatus(
            String bucketName, String objectKey, String fileName) {

        underTest.putOneObject(bucketName, objectKey, IoUtils.readFile(format("src/test/resources/%s", fileName)), (response, err) -> {
            assertNotNull(response);
            assertTrue(response.sdkHttpResponse().isSuccessful());
            assertEquals(200, response.sdkHttpResponse().statusCode());
            assertNotNull(response.eTag());
        });
    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', filePath=''{2}''")
    @CsvSource({"saman-aws-tutorial-bucket, aws-object, temp-test-result"})
    @DisplayName("get one object async")
    @Order(8)
    void getOneObject_GivenBucketNameAndObjectKeyAndFilePathAsParam_WhenSendAsyncGetObjectRequest_ThenReturnTheByteArray(String bucketName, String objectKey, String filePath) {
        underTest.getOneObject(bucketName, objectKey, (response, err) -> {
            assertNotNull(response);
            IoUtils.createFile(format("src/test/resources/%s_%d.txt", filePath, System.currentTimeMillis()), response.asByteArray());
            underTest.deleteOneObject(bucketName, objectKey);
        });
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"saman-aws-tutorial-bucket"})
    @DisplayName("delete one bucket")
    @Order(9)
    void deleteOneBucket_GivenBucketNameAsParam_WhenSendDeleteRequest_ThenReturnNoContentStatus(String name) {
        Optional<DeleteBucketResponse> response = underTest.deleteOneBucket(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(204, it.sdkHttpResponse().statusCode());
        });
    }

}
