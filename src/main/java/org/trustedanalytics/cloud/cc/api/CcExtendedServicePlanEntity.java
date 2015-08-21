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
    private String service_guid;

    @JsonProperty("extra")
    private String extra;

    @JsonProperty("unique_id")
    private String unique_id;

    @JsonProperty("public")
    private Boolean public_;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("service_url")
    private String service_url;

    @JsonProperty("service_instances_url")
    private String service_instances_url;

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

    public String getService_guid() {
        return service_guid;
    }

    public void setService_guid(String service_guid) {
        this.service_guid = service_guid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public Boolean getPublic_() {
        return public_;
    }

    public void setPublic_(Boolean public_) {
        this.public_ = public_;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getService_url() {
        return service_url;
    }

    public void setService_url(String service_url) {
        this.service_url = service_url;
    }

    public String getService_instances_url() {
        return service_instances_url;
    }

    public void setService_instances_url(String service_instances_url) {
        this.service_instances_url = service_instances_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
