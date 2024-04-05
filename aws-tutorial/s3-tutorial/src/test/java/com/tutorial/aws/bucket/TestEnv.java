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

import io.vavr.control.Try;

import java.util.Properties;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class TestEnv {

    public static Properties loadCredentials() {
        return Try.of(() -> System.getProperty("vendor"))
                .map(s -> switch (s) {
                    case "ENV" -> ENV.PROPERTIES;
                    case "CLI" -> CLI.PROPERTIES;
                    case "Localstack" -> LocalStack.PROPERTIES;
                    default -> throw new RuntimeException("Vendor is unknown");
                })
                .getOrElse(() -> {
                    System.setProperty("vendor", "Localstack");
                    return LocalStack.PROPERTIES;
                });
    }

    private static class LocalStack {
        private static final Properties PROPERTIES = new Properties();

        static {
            PROPERTIES.put("region", "us-west-2");
            PROPERTIES.put("url", "http://localhost:4566");
            PROPERTIES.put("accessKey", "fake");
            PROPERTIES.put("secretKey", "fake");
        }
    }

    private static class ENV {
        private static final Properties PROPERTIES = new Properties();

        static {
            PROPERTIES.put("region", "eu-central-1");
            PROPERTIES.put("accessKey", System.getenv("ACCESS_KEY_ID"));
            PROPERTIES.put("secretKey", System.getenv("SECRET_ACCESS_KEY"));
        }
    }

    private static class CLI {
        private static final Properties PROPERTIES = new Properties();

        static {
            PROPERTIES.put("region", "eu-central-1");
        }
    }
}
