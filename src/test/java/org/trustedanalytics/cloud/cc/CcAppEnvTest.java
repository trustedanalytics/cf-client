/**
 * Copyright (c) 2015 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trustedanalytics.cloud.cc;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.trustedanalytics.cloud.cc.api.CcAppEnv;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class) public class CcAppEnvTest {

    String exampleJson;
    String extendedExampleJson;
    CcAppEnv ccAppEnv;
    CcAppEnv ccAppEnvFromExtendedJson;

    @Before
    public void setup() throws IOException {
        exampleJson = "{\n" +
            "      \"user-provided\": [\n" +
            "        {\n" +
            "             \"name\": \"sso\",\n" +
            "             \"label\": \"user-provided\",\n" +
            "             \"tags\": [],\n" +
            "             \"credentials\": {\n" +
            "               \"apiEndpoint\": \"http://api.example.eu\"\n" +
            "             }\n" +
            "        }]}";

        extendedExampleJson =
                    "{\n" +
                    " \"VCAP_SERVICES\": {\n" +
                    "  \"cdh\": [\n" +
                    "   {\n" +
                    "    \"label\": \"cdh\",\n" +
                    "    \"name\": \"cdh-xyz\",\n" +
                    "    \"plan\": \"shared\",\n" +
                    "    \"tags\": [\n" +
                    "     \"simple\",\n" +
                    "     \"shared\"\n" +
                    "    ]\n" +
                    "   }\n" +
                    "  ],\n" +
                    "  \"rstudio\": [\n" +
                    "   {\n" +
                    "    \"credentials\": {\n" +
                    "     \"hostname\": \"10.11.5.251\",\n" +
                    "     \"password\": \"xyz\",\n" +
                    "     \"port\": \"3736\",\n" +
                    "     \"ports\": {\n" +
                    "      \"8787/tcp\": \"3736\"\n" +
                    "     },\n" +
                    "     \"uri\": \"rstudio://xyz\",\n" +
                    "     \"username\": \"xyz\"\n" +
                    "    },\n" +
                    "    \"label\": \"rstudio\",\n" +
                    "    \"name\": \"rstudio-1\",\n" +
                    "    \"plan\": \"free\",\n" +
                    "    \"tags\": [\n" +
                    "     \"rstudio\",\n" +
                    "     \"r\"\n" +
                    "    ]\n" +
                    "   },\n" +
                    "   {\n" +
                    "    \"credentials\": {\n" +
                    "     \"hostname\": \"10.12.5.251\",\n" +
                    "     \"password\": \"xyz\",\n" +
                    "     \"port\": \"3772\",\n" +
                    "     \"ports\": {\n" +
                    "      \"8787/tcp\": \"3772\"\n" +
                    "     },\n" +
                    "     \"uri\": \"rstudio://xyz\",\n" +
                    "     \"username\": \"xyz\"\n" +
                    "    },\n" +
                    "    \"label\": \"rstudio\",\n" +
                    "    \"name\": \"rstudio-2\",\n" +
                    "    \"plan\": \"free\",\n" +
                    "    \"tags\": [\n" +
                    "     \"rstudio\",\n" +
                    "     \"r\"\n" +
                    "    ]\n" +
                    "   },\n" +
                    "   {\n" +
                    "    \"credentials\": {\n" +
                    "     \"hostname\": \"10.13.5.251\",\n" +
                    "     \"password\": \"xyz\",\n" +
                    "     \"port\": \"3752\",\n" +
                    "     \"ports\": {\n" +
                    "      \"8787/tcp\": \"3752\"\n" +
                    "     },\n" +
                    "     \"uri\": \"rstudio://xyz\",\n" +
                    "     \"username\": \"xyz\"\n" +
                    "    },\n" +
                    "    \"label\": \"rstudio\",\n" +
                    "    \"name\": \"rstudio-3\",\n" +
                    "    \"plan\": \"free\",\n" +
                    "    \"tags\": [\n" +
                    "     \"rstudio\",\n" +
                    "     \"r\"\n" +
                    "    ]\n" +
                    "   }\n" +
                    "  ]\n" +
                    " }\n" +
                    "}\n";

        ObjectMapper objectMapper = new ObjectMapper();
        ccAppEnv = new CcAppEnv(objectMapper.readValue(exampleJson, Object.class));
        ccAppEnvFromExtendedJson = new CcAppEnv(objectMapper.readValue(extendedExampleJson, Object.class));
    }

    @Test public void getValueByFilter_simpleJson() throws IOException {
        String expectedApiEndpoint = "[\"http://api.example.eu\"]";
        String actualApiEndpoint = ccAppEnv.getValueByFilter("$..[?(@.name=='sso')]..credentials..apiEndpoint").toString();
        assertEquals(expectedApiEndpoint, actualApiEndpoint);
    }

    @Test public void getPropertyFromNodeSpecifiedByKeyAndValue_simpleJson() throws IOException {
        String expectedApiEndpoint = "[\"http://api.example.eu\"]";
        String actualApiEndpoint = ccAppEnv.getPropertyFromNodeSpecifiedByKeyAndValue("name", "sso", "credentials").findValues("apiEndpoint").toString();
        assertEquals(expectedApiEndpoint, actualApiEndpoint);
    }

    @Test public void findCredentialsPropertyByServiceLabel_simpleJson() throws IOException {
        String expectedApiEndpoint = "http://api.example.eu";
        String actualApiEndpoint = ccAppEnv.findCredentialsPropertyByServiceLabel("user-provided", "apiEndpoint");
        assertEquals(expectedApiEndpoint, actualApiEndpoint);
    }

    @Test public void findCredentialsPropertyByServiceName_simpleJson() throws IOException {
        String expectedApiEndpoint = "http://api.example.eu";
        String actualApiEndpoint = ccAppEnv.findCredentialsPropertyByServiceName("sso", "apiEndpoint");
        assertEquals(expectedApiEndpoint, actualApiEndpoint);
    }

    @Test public void getValueByFilter_extendedJson() throws IOException {
        String expectedHostname = "[\"10.11.5.251\"]";
        String actualHostname = ccAppEnvFromExtendedJson.getValueByFilter("$..[?(@.name=='rstudio-1')]..credentials..hostname").toString();
        assertEquals(expectedHostname, actualHostname);
    }

    @Test public void getPropertyFromNodeSpecifiedByKeyAndValue_extendedJson() throws IOException {
        String expectedHostname = "[\"10.11.5.251\"]";
        String actualHostname = ccAppEnvFromExtendedJson.getPropertyFromNodeSpecifiedByKeyAndValue("name", "rstudio-1", "credentials").findValues("hostname").toString();
        assertEquals(expectedHostname, actualHostname);
    }

    @Test public void findCredentialsPropertyByServiceLabel_extendedJson() throws IOException {
        String expectedHostname = "10.11.5.251";
        String actualHostname = ccAppEnvFromExtendedJson.findCredentialsPropertyByServiceLabel("rstudio", "hostname");
        assertEquals(expectedHostname, actualHostname);
    }

    @Test public void findCredentialsPropertyByServiceName_extendedJson() throws IOException {
        String expectedHostname = "10.11.5.251";
        String actualHostname = ccAppEnvFromExtendedJson.findCredentialsPropertyByServiceName("rstudio-1", "hostname");
        assertEquals(expectedHostname, actualHostname);
    }
}
