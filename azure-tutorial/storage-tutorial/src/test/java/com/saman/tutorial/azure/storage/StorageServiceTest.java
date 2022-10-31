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

package com.saman.tutorial.azure.storage;

import org.junit.jupiter.api.*;

import java.util.Locale;
import java.util.Properties;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
@DisplayName("Storage Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class StorageServiceTest {

    private static final StorageService underTest = new StorageService(TestEnv.loadAccountProperties());

    @BeforeAll
    static void setUp() {
        assertNotNull(underTest);
    }

    @Test
    @DisplayName("create container")
    @Order(1)
    void createContainer_GivenUniqueName_WhenCreateContainer_ThenReturnContainerClient() {
        var givenContainerName = "saman-azure-tutorial-storage";

        var result = underTest.createContainer(givenContainerName);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("upload blob")
    @Order(2)
    void uploadFile_GivenContainerNameAndFilePath_WhenUploadFile_ThenReturnBlobUrl() {
        var givenContainerName = "saman-azure-tutorial-storage";
        var givenFileDirectory = "src/test/resources/";
        var givenFileName = "test-file.txt";

        var result = underTest.uploadFile(givenContainerName, givenFileDirectory, givenFileName);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("get list of blob names")
    @Order(3)
    void getContainerBlobs_GivenUniqueName_WhenGetBlobsListName_ThenReturnListOfContainerNames() {
        var givenContainerName = "saman-azure-tutorial-storage";

        var result = underTest.getContainerBlobs(givenContainerName);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("download file")
    @Order(4)
    void downloadFile_GivenParameter_GivenContainerNameAndSourceNameAndTargetPath_WhenDownloadFile_ThenReturnBlobProperties() {
        var givenContainerName = "saman-azure-tutorial-storage";
        var givenSourceName = "test-file.txt";
        var givenTargetPath = format("%s%s_%d.txt", "target/", "temp-test-result", System.currentTimeMillis());

        var result = underTest.downloadFile(givenContainerName, givenSourceName, givenTargetPath);
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("delete file")
    @Order(5)
    void deleteFile_GivenContainerNameAndFileName_WhenDeleteFile_ThenRunSuccessful() {
        var givenContainerName = "saman-azure-tutorial-storage";
        var givenFileName = "temp-test.txt";

        assertDoesNotThrow(() -> underTest.deleteFile(givenContainerName, givenFileName));

    }

    @Test
    @DisplayName("delete container")
    @Order(6)
    void deleteContainer_GivenContainerName_WhenDeleteContainer_ThenRunSuccessful() {
        var givenContainerName = "saman-azure-tutorial-storage";

        assertDoesNotThrow(() -> underTest.deleteContainer(givenContainerName));
    }
}