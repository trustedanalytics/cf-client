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

import org.trustedanalytics.cloud.cc.api.CcAppStatus;
import org.trustedanalytics.cloud.cc.api.CcAppSummary;
import org.trustedanalytics.cloud.cc.api.CcExtendedService;
import org.trustedanalytics.cloud.cc.api.CcServiceBindingList;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.queries.FilterExpander;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

@Headers("Accept: application/json")
public interface CcApplicationResource {

    @RequestLine("GET /v2/apps")
    Page<CcExtendedService> getApplications();

    @RequestLine("GET /v2/apps/{app}/summary")
    CcAppSummary getAppSummary(@Param("app") UUID app);

    @RequestLine("GET /v2/apps/{app}/service_bindings")
    CcServiceBindingList getAppBindings(@Param("app") UUID app);

    @RequestLine("GET /v2/apps/{app}/service_bindings?q={query}")
    CcServiceBindingList getAppBindings(@Param("app") UUID app,
        @Param(value = "query", expander = FilterExpander.class) FilterQuery query);

    @RequestLine("POST /v2/apps/{app}/restage")
    void restageApp(@Param("app") UUID app);

    @RequestLine("DELETE /v2/apps/{app}?recursive=true")
    void deleteApp(@Param("app") UUID app);

    @RequestLine("PUT /v2/apps/{app}")
    void switchApp(@Param("app") UUID app, CcAppStatus appStatus);

    //Returned object can have undefined structure and therefore it is mapped to Object class
    @RequestLine("GET /v2/apps/{app}/env")
    Object getAppEnv(@Param("app") UUID app);
}
