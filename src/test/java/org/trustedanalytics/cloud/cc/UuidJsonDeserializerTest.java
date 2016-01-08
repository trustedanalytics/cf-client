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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.trustedanalytics.cloud.cc.api.CcMetadata;
import org.trustedanalytics.cloud.cc.api.utils.UuidJsonDeserializer;

import java.io.IOException;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UuidJsonDeserializerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void uuidMapping_correctUuid() throws IOException {
        UUID randomUuid = UUID.randomUUID();
        String json = "{\"guid\":\"" + randomUuid + "\"}";

        CcMetadata metadata = objectMapper.readValue(json, CcMetadata.class);
        assertEquals(randomUuid, metadata.getGuid());
    }

    @Test
    public void uuidMapping_incorrectUuid_emptyString() throws IOException {

        String json = "{\"guid\":\"\"}";

        CcMetadata metadata = objectMapper.readValue(json, CcMetadata.class);
        assertEquals(UuidJsonDeserializer.ARTIFICIAL_USER_GUID, metadata.getGuid());
    }

    @Test
    public void uuidMapping_incorrectUuid_invalidFormat() throws IOException {

        String json = "{\"guid\":\"5f9074f8c019e-4e5a-8dc40b3-7b651f57b\"}";

        CcMetadata metadata = objectMapper.readValue(json, CcMetadata.class);
        assertEquals(UuidJsonDeserializer.ARTIFICIAL_USER_GUID, metadata.getGuid());
    }

    @Test
    public void uuidMapping_incorrectUuid_noValue() throws IOException {

        String json = "{}";

        CcMetadata metadata = objectMapper.readValue(json, CcMetadata.class);
        assertEquals(null, metadata.getGuid());
    }

    @Test
    public void uuidMapping_incorrectUuid_nullValue() throws IOException {

        String json = "{\"guid\":\"null\"}";

        CcMetadata metadata = objectMapper.readValue(json, CcMetadata.class);
        assertEquals(UuidJsonDeserializer.ARTIFICIAL_USER_GUID, metadata.getGuid());
    }
}
