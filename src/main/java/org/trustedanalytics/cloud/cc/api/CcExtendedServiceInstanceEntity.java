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
package org.trustedanalytics.cloud.cc.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CcExtendedServiceInstanceEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("credentials")
    private Object credentials;

    @JsonProperty("service_plan_guid")
    private UUID servicePlanGuid;

    @JsonProperty("service_plan")
    private CcExtendedServicePlan servicePlan;

    @JsonProperty("space_guid")
    private UUID spaceGuid;

    @JsonProperty("gateway_data")
    private String gatewayData;

    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonProperty("type")
    private String type;

    @JsonProperty("last_operation")
    private Object lastOperation;

    @JsonProperty("tags")
    private Object tags;

    @JsonProperty("space_url")
    private String spaceUrl;

    @JsonProperty("service_plan_url")
    private String servicePlanUrl;

    @JsonProperty("service_bindings_url")
    private String serviceBindingsUrl;

    @JsonProperty("service_keys_url")
    private String serviceKeysUrl;

    @JsonProperty("service_keys")
    private Collection<CcServiceKey> serviceKeys;

}
