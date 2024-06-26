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

package com.tutorial.aws.sqs.utils;

import io.vavr.control.Try;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import static java.util.Arrays.stream;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public final class IoUtils {

    private IoUtils() {
    }

    public static byte[] readFile(String path) {
        return Try.withResources(() -> new FileInputStream(path))
                .of(InputStream::readAllBytes)
                .get();
    }

    public static void createFile(String name, byte[] content) {
        Try.withResources(() -> new FileOutputStream(name))
                .of(outputStream -> {
                    outputStream.write(content);
                    return outputStream;
                });
    }

    public static void createFile(String name, String[] content) {
        Try.withResources(() -> new FileOutputStream(name))
                .of(outputStream -> {
                    stream(content).forEach(s -> Try.run(() -> outputStream.write(s.getBytes())));
                    return outputStream;
                });
    }
}
