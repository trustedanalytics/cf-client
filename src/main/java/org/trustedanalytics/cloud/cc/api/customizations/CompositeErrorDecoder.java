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

import com.google.common.collect.ImmutableList;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.Collection;
import java.util.Collections;

public class CompositeErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
    private final Collection<ErrorDecoderHandler> handlers;

    public CompositeErrorDecoder() {
        this(Collections.emptyList());
    }

    public CompositeErrorDecoder(ErrorDecoderHandler... handlers) {
        this.handlers = ImmutableList.copyOf(handlers);
    }

    public CompositeErrorDecoder(Collection<ErrorDecoderHandler> handlers) {
        this.handlers = ImmutableList.copyOf(handlers);
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        return handlers.stream().filter(handler -> handler.test(response))
            .findFirst()
            .map(handler -> handler.apply(methodKey, response))
            .orElse(defaultDecoder.decode(methodKey, response));
    }
}
