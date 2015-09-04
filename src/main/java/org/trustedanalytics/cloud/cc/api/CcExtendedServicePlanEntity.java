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

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcExtendedServicePlanEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("free")
    private Boolean free;

    @JsonProperty("description")
    private String description;

    @JsonProperty("service_guid")
    private String serviceGuid;

    @JsonProperty("extra")
    private String extra;

    @JsonProperty("unique_id")
    private String uniqueId;

    @JsonProperty("public")
    private Boolean publicStatus;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("service_url")
    private String serviceUrl;

    @JsonProperty("service_instances_url")
    private String serviceInstancesUrl;

    @JsonProperty("metadata")
    private CcMetadata metadata;

    public CcMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(CcMetadata metadata) {
        this.metadata = metadata;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Boolean publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceInstancesUrl() {
        return serviceInstancesUrl;
    }

    public void setServiceInstancesUrl(String serviceInstancesUrl) {
        this.serviceInstancesUrl = serviceInstancesUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
