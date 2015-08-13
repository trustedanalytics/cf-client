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

package org.trustedanalytics.cloud.cc.api.resources;

import org.trustedanalytics.cloud.cc.api.CcNewServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcServiceBindingList;
import org.trustedanalytics.cloud.cc.api.queries.FilterExpander;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

@Headers("Accept: application/json")
public interface CcServiceBindingResource {

    @RequestLine("POST /v2/service_bindings")
    @Headers("Content-Type: application/json")
    CcServiceBinding createServiceBinding(CcNewServiceBinding binding);

    @RequestLine("DELETE /v2/service_bindings/{binding}")
    void deleteServiceBinding(@Param("binding") UUID binding);

    @RequestLine("GET /v2/service_bindings?q={query}")
    CcServiceBindingList getServiceBindings(
        @Param(value = "query", expander = FilterExpander.class) FilterQuery query);
}
