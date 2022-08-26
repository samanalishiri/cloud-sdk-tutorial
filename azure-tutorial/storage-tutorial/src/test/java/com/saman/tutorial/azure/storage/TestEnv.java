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

import io.vavr.control.Try;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-08-01
 */
public class TestEnv {

    public static Properties loadAccountProperties() {
        return Try.of(() -> System.getProperty("vendor"))
                .map(s -> switch (s) {
                    case "AzureEnv" -> AzureEnv.PROPERTIES;
                    case "Azurite" -> Azurite.PROPERTIES;
                    default -> throw new RuntimeException("Vendor is unknown");
                })
                .getOrElse(() -> {
                    System.setProperty("vendor", "Azurite");
                    return Azurite.PROPERTIES;
                });
    }

    private static class Azurite {
        private static final Properties PROPERTIES = new Properties() {{
            put("accountName", "devstoreaccount1");
            put("accountKey", "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==");
            put("connectionString", "AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;");
            put("url", "http://127.0.0.1:10000/devstoreaccount1");
        }};
    }

    private static class AzureEnv {
        private static final Properties PROPERTIES = new Properties() {{
            put("accountName", System.getenv("ACCOUNT_NAME"));
            put("accountKey", System.getenv("ACCOUNT_KEY"));
            put("connectionString", System.getenv("AZURE_STORAGE_CONNECTION_STRING"));
            put("url", String.format(Locale.ROOT, "https://%s.blob.core.windows.net", System.getenv("ACCOUNT_NAME")));
        }};
    }
}
