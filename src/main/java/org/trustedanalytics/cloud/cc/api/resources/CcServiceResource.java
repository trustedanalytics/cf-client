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

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import org.trustedanalytics.cloud.cc.api.CcExtendedService;
import org.trustedanalytics.cloud.cc.api.CcExtendedServicePlan;
import org.trustedanalytics.cloud.cc.api.CcNewServiceKey;
import org.trustedanalytics.cloud.cc.api.CcPlanVisibility;
import org.trustedanalytics.cloud.cc.api.CcServiceKey;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.CcNewServiceInstance;
import org.trustedanalytics.cloud.cc.api.CcExtendedServiceInstance;
import org.trustedanalytics.cloud.cc.api.queries.FilterExpander;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;

import java.net.URI;
import java.util.UUID;

@Headers("Accept: application/json")
public interface CcServiceResource {

    @RequestLine("GET /v2/services/{service}?inline-relations-depth=1")
    CcExtendedService getService(@Param("service") UUID service);

    @RequestLine("GET /v2/services")
    Page<CcExtendedService> getServices();

    @RequestLine("GET /v2/services?q={query}")
    Page<CcExtendedService> getServices(
            @Param(value = "query", expander = FilterExpander.class) FilterQuery query);

    @RequestLine("GET")
    Page<CcExtendedService> getServices(URI nextPageUrl);

    @RequestLine("GET /v2/services/{service}/service_plans")
    Page<CcExtendedServicePlan> getExtendedServicePlans(@Param("service") UUID service);

    @RequestLine("GET")
    Page<CcExtendedServicePlan> getExtendedServicePlans(URI nextPageUrl);

    @RequestLine("GET /v2/service_instances")
    Page<CcExtendedServiceInstance> getExtendedServiceInstances();

    @RequestLine("GET /v2/service_instances?q={query}")
    Page<CcExtendedServiceInstance> getExtendedServiceInstances(
        @Param(value = "query", expander = FilterExpander.class) FilterQuery query);

    @RequestLine("GET /v2/service_instances?q={query}&inline-relations-depth={depth}")
    Page<CcExtendedServiceInstance> getExtendedServiceInstances(
        @Param(value = "query", expander = FilterExpander.class) FilterQuery query,
        @Param("depth") int depth);

    @RequestLine("GET /v2/service_instances?inline-relations-depth={depth}")
    Page<CcExtendedServiceInstance> getExtendedServiceInstances(@Param("depth") int depth);

    @RequestLine("GET")
    Page<CcExtendedServiceInstance> getExtendedServiceInstances(URI nextPageUrl);

    @RequestLine("POST /v2/service_instances")
    CcExtendedServiceInstance createServiceInstance(CcNewServiceInstance instance);

    @RequestLine("GET /v2/service_instances/{instance}")
    CcExtendedServiceInstance getServiceInstance(@Param("instance") UUID instanceGuid);

    @RequestLine("DELETE /v2/service_instances/{instance}")
    void deleteServiceInstance(@Param("instance") UUID instance);

    @RequestLine("GET /v2/service_keys")
    Page<CcServiceKey> getServiceKeys();

    @RequestLine("GET /v2/service_keys?q={query}")
    Page<CcServiceKey> getServiceKeys(
        @Param(value = "query", expander = FilterExpander.class) FilterQuery query);

    @RequestLine("GET")
    Page<CcServiceKey> getServiceKeys(URI nextPageUrl);

    @RequestLine("POST /v2/service_keys")
    CcServiceKey createServiceKey(CcNewServiceKey serviceKey);

    @RequestLine("DELETE /v2/service_keys/{key}")
    void deleteServiceKey(@Param("key") UUID keyGuid);

    @RequestLine("POST /v2/service_plan_visibilities")
    @Headers("Content-Type: application/json")
    @Body("%7B\"service_plan_guid\": \"{service_plan_guid}\", \"organization_guid\": \"{organization_guid}\"%7D")
    CcPlanVisibility setServicePlanVisibility(@Param("service_plan_guid") UUID servicePlanGuid, @Param("organization_guid") UUID organizationGuid);

    @RequestLine("GET /v2/service_plan_visibilities?q={query}")
    Page<CcPlanVisibility> getServicePlanVisibility(@Param(value = "query", expander = FilterExpander.class) FilterQuery query);

    @RequestLine("GET")
    Page<CcPlanVisibility> getServicePlanVisibility(URI nextPageUrl);
}
