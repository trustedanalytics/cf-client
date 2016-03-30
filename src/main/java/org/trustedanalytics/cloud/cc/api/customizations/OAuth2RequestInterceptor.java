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

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.function.Supplier;

public class OAuth2RequestInterceptor implements RequestInterceptor {
    private final Supplier<String> tokenSupplier;

    public OAuth2RequestInterceptor(String token) {
        this(() -> token);
    }

    public OAuth2RequestInterceptor(Supplier<String> tokenSupplier) {
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "bearer " + tokenSupplier.get());
    }
}
