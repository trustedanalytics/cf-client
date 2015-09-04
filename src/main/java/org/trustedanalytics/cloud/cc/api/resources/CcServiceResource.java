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

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.trustedanalytics.cloud.cc.api.CcExtendedService;
import org.trustedanalytics.cloud.cc.api.CcExtendedServicePlan;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.CcNewServiceInstance;
import org.trustedanalytics.cloud.cc.api.CcExtendedServiceInstance;

import java.net.URI;
import java.util.UUID;

@Headers("Accept: application/json")
public interface CcServiceResource {

    @RequestLine("GET /v2/services/{service}?inline-relations-depth=1")
    String getService(@Param("service") UUID service);

    @RequestLine("GET /v2/services")
    Page<CcExtendedService> getServices();

    @RequestLine("GET {nextPageUrl}")
    Page<CcExtendedService> getServices(URI nextPageUrl);

    @RequestLine("GET /v2/services/{service}/service_plans")
    Page<CcExtendedServicePlan> getExtendedServicePlans(@Param("service") UUID service);

    @RequestLine("GET {nextPageUrl}")
    Page<CcExtendedServicePlan> getExtendedServicePlans(URI nextPageUrl);

    @RequestLine("POST /v2/service_instances")
    CcExtendedServiceInstance createServiceInstance(CcNewServiceInstance instance);

    @RequestLine("DELETE /v2/service_instances/{instance}")
    void deleteServiceInstance(@Param("instance") UUID instance);
}
