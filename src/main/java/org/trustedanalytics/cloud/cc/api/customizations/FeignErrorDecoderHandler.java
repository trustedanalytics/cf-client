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
package org.trustedanalytics.cloud.cc.api.customizations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

import feign.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Stream;

public class FeignErrorDecoderHandler implements ErrorDecoderHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoderHandler.class);
    private static final String EMPTY_STRING = "";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static final String FAILED_ACTION_MESSAGE = "Unable to perform requested action";

    private final Set<String> fieldPaths;


    public FeignErrorDecoderHandler(String... fieldPaths) {
        this.fieldPaths = ImmutableSet.copyOf(fieldPaths);
    }

    public FeignErrorDecoderHandler(Set<String> fieldPaths) {
        this.fieldPaths = ImmutableSet.copyOf(fieldPaths);
    }

    @Override
    public Exception apply(String methodKey, Response response) {
        return decodeExpectedFields(response).reduce((field1, field2) -> String.join(", ", field1, field2))
            .map(message -> (Exception) new FeignResponseException(response.status(), message))
            .orElse(new FeignResponseException(response.status(), FAILED_ACTION_MESSAGE));
    }

    @Override
    public boolean test(Response response) {
        // If response body is not repeatable we need to guess whether we can decode it
        return !response.body().isRepeatable() || decodeExpectedFields(response).findAny()
            .isPresent();
    }

    private Stream<String> decodeExpectedFields(Response response) {
        String body = EMPTY_STRING;
        try {
            body = IOUtils.toString(response.body().asInputStream(), Charsets.UTF_8.toString());
            JsonNode json = MAPPER.readValue(body, JsonNode.class);
            return fieldPaths.stream()
                .map(json::path)
                .filter(JsonNode::isValueNode)
                .map(JsonNode::asText);
        } catch (Exception e) {
            LOGGER.info("Unable to deserialize response: {}", body);
            return Stream.empty();
        }
    }
}
