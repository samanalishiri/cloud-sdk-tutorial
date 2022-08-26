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

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.blob.specialized.BlobClientBase;
import com.azure.storage.common.StorageSharedKeyCredential;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class StorageService {

    private final Logger logger = LoggerFactory.getLogger(StorageService.class.getSimpleName());

    private final BlobServiceClient client;

    public StorageService(Properties properties) {
        requireNonNull(properties);

        var accountName = properties.getProperty("accountName");
        var accountKey = properties.getProperty("accountKey");
        var connectionString = properties.getProperty("connectionString");
        var url = properties.getProperty("url");

        client = new BlobServiceClientBuilder()
                .endpoint(url)
                .credential(new StorageSharedKeyCredential(accountName, accountKey))
                .connectionString(connectionString)
                .buildClient();
    }

    private static void isBlank(String name, String message) {
        requireNonNull(name);
        if (name.isBlank())
            throw new IllegalArgumentException(message);
    }

    public Optional<BlobContainerClient> createContainer(String name) {
        isBlank(name, "name is invalid");

        return Try.of(() -> client.createBlobContainerIfNotExists(name))
                .onSuccess(container -> logger.info("container {} was created: {}", name, container.exists()))
                .onFailure(ex -> logger.error("create container {} failed: {}", name, ex.getMessage()))
                .toJavaOptional();
    }

    public Optional<String> uploadFile(String containerName, String fileDirectory, String fileName) {
        isBlank(containerName, "container name is invalid");
        isBlank(fileDirectory, "file directory is invalid");
        isBlank(fileName, "file name is invalid");

        return Try.of(() -> client.getBlobContainerClient(containerName).getBlobClient(fileName))
                .andThen(blobClient -> blobClient.uploadFromFile(fileDirectory + fileName))
                .onFailure(ex -> logger.error("upload {} failed: {}", fileName, ex.getMessage()))
                .onSuccess(blobClient -> logger.info("file {} was uploaded: {}", fileName, blobClient.getBlobUrl()))
                .map(BlobClientBase::getBlobUrl)
                .toJavaOptional();
    }

    public List<String> getContainerBlobs(String containerName) {
        isBlank(containerName, "container name is invalid");

        return client.getBlobContainerClient(containerName)
                .listBlobs()
                .stream()
                .map(BlobItem::getName)
                .toList();
    }

    public Optional<BlobProperties> downloadFile(String containerName, String sourceName, String targetPath) {
        isBlank(containerName, "container name is invalid");
        isBlank(sourceName, "source name is invalid");
        isBlank(targetPath, "target path is invalid");

        return Try.of(() -> client.getBlobContainerClient(containerName).getBlobClient(sourceName))
                .onFailure(ex -> logger.error("download {} failed: {}", sourceName, ex.getMessage()))
                .map(blobClient -> blobClient.downloadToFile(targetPath))
                .toJavaOptional();
    }

    public void deleteFile(String containerName, String fileName) {
        isBlank(containerName, "container name is invalid");
        isBlank(fileName, "file name is invalid");

        Try.of(() -> client.getBlobContainerClient(containerName).getBlobClient(fileName))
                .onSuccess(BlobClientBase::deleteIfExists)
                .onFailure(ex -> logger.error("delete {} failed: {}", fileName, ex.getMessage()));
    }

    public void deleteContainer(String containerName) {
        isBlank(containerName, "container name is invalid");

        Try.of(() -> client.deleteBlobContainerIfExists(containerName))
                .onSuccess(a -> logger.info("container {} was deleted", containerName))
                .onFailure(ex -> logger.error("delete container {} failed: {}", containerName, ex.getMessage()));
    }
}