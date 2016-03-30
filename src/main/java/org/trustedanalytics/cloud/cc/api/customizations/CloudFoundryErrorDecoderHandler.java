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

import feign.FeignException;
import feign.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CloudFoundryErrorDecoderHandler implements ErrorDecoderHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudFoundryErrorDecoderHandler.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Exception apply(String methodKey, Response response) {
        try {
            JsonNode node = MAPPER.readValue(response.body().asInputStream(), JsonNode.class);
            int httpCode = response.status();
            int cfCode = node.path("code").asInt();
            String description = node.path("description").textValue();
            String errorCode = node.path("error_code").textValue();

            if(StringUtils.isNotBlank(description)) {
                LOGGER.error("method: {}, httpCode: {}, cfCode: {}, description: {}, error_code: {}",
                    methodKey, httpCode, cfCode, description, errorCode);
                return new CloudFoundryException(response.status(), cfCode, description, errorCode);
            }
        } catch (IOException e) {
            LOGGER.debug("Unable to deserialize response:", e);
        }
        return FeignException.errorStatus(methodKey, response);
    }

    @Override
    public boolean test(Response response) {
        return true;
    }
}
