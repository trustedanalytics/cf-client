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
package org.trustedanalytics.cloud.cc.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

//This class help to handle situation when GUID will be incorrect
public class UuidJsonDeserializer extends JsonDeserializer<UUID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UuidJsonDeserializer.class);

    public static final UUID ARTIFICIAL_USER_GUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public UUID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        try {
            return UUID.fromString(jsonParser.getValueAsString());
        }
        catch (IllegalArgumentException e) {
            LOGGER.debug("Unable to deserialize GUID: {}, exception: {}", jsonParser.getValueAsString(), e);
            return ARTIFICIAL_USER_GUID;
        }
    }
}
