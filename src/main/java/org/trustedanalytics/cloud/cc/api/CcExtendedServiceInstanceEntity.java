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

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcExtendedServiceInstanceEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("credentials")
    private Object credentials;

    @JsonProperty("service_plan_guid")
    private UUID servicePlanGuid;

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

    public String getServiceKeysUrl() {
        return serviceKeysUrl;
    }

    public void setServiceKeysUrl(String serviceKeysUrl) {
        this.serviceKeysUrl = serviceKeysUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCredentials() {
        return credentials;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public UUID getServicePlanGuid() {
        return servicePlanGuid;
    }

    public void setServicePlanGuid(UUID servicePlanGuid) {
        this.servicePlanGuid = servicePlanGuid;
    }

    public UUID getSpaceGuid() {
        return spaceGuid;
    }

    public void setSpaceGuid(UUID spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    public String getGatewayData() {
        return gatewayData;
    }

    public void setGatewayData(String gatewayData) {
        this.gatewayData = gatewayData;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(Object lastOperation) {
        this.lastOperation = lastOperation;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public String getSpaceUrl() {
        return spaceUrl;
    }

    public void setSpaceUrl(String spaceUrl) {
        this.spaceUrl = spaceUrl;
    }

    public String getServicePlanUrl() {
        return servicePlanUrl;
    }

    public void setServicePlanUrl(String servicePlanUrl) {
        this.servicePlanUrl = servicePlanUrl;
    }

    public String getServiceBindingsUrl() {
        return serviceBindingsUrl;
    }

    public void setServiceBindingsUrl(String serviceBindingsUrl) {
        this.serviceBindingsUrl = serviceBindingsUrl;
    }
}
