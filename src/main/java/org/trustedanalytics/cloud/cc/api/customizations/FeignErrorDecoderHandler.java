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
import com.google.common.collect.ImmutableSet;

import feign.FeignException;
import feign.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

public class FeignErrorDecoderHandler implements ErrorDecoderHandler  {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoderHandler.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Set<String> fieldPaths;

    public FeignErrorDecoderHandler(String ...fieldPaths) {
        this.fieldPaths = ImmutableSet.copyOf(fieldPaths);
    }

    public FeignErrorDecoderHandler(Set<String> fieldPaths) {
        this.fieldPaths = ImmutableSet.copyOf(fieldPaths);
    }

    @Override
    public Exception apply(String methodKey, Response response) {
        return nodes(response)
            .map(JsonNode::asText)
            .reduce((s1, s2) -> String.join(", ", s1, s2))
            .map(message -> (Exception) new FeignResponseException(response.status(), message))
            .orElse(FeignException.errorStatus(methodKey, response));
    }

    @Override
    public boolean test(Response response) {
        // In some cases body can be read only once and therefore we need to try best
        // effort approach when checking whether we can decode exception.
        return !response.body().isRepeatable() || nodes(response).findAny().isPresent();
    }

    private Stream<JsonNode> nodes(Response response) {
        try {
            final JsonNode json = MAPPER.readValue(response.body().asInputStream(), JsonNode.class);
            return fieldPaths.stream()
                .map(json::path)
                .filter(JsonNode::isValueNode);
        } catch (IOException e) {
            LOGGER.debug("Unable to deserialize response:", e);
            return Stream.empty();
        }
    }
}
