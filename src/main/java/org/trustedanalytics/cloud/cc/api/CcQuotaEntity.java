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
public class CcQuotaEntity {

    @JsonProperty("total_services")
    private int totalServices;

    @JsonProperty("total_routes")
    private int totalRoutes;

    @JsonProperty("memory_limit")
    private int memoryLimit;

    @JsonProperty("name")
    private String name;

    @JsonProperty("non_basic_services_allowed")
    private Boolean nonBasicServicesAllowed;

    @JsonProperty("trial_db_allowed")
    private Boolean trialDbAllowed;

    @JsonProperty("instance_memory_limit")
    private int instanceMemoryLimit;

    public int getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(int totalServices) {
        this.totalServices = totalServices;
    }

    public int getTotalRoutes() {
        return totalRoutes;
    }

    public void setTotalRoutes(int totalRoutes) {
        this.totalRoutes = totalRoutes;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNonBasicServicesAllowed() {
        return nonBasicServicesAllowed;
    }

    public void setNonBasicServicesAllowed(Boolean nonBasicServicesAllowed) {
        this.nonBasicServicesAllowed = nonBasicServicesAllowed;
    }

    public Boolean getTrialDbAllowed() {
        return trialDbAllowed;
    }

    public void setTrialDbAllowed(Boolean trialDbAllowed) {
        this.trialDbAllowed = trialDbAllowed;
    }

    public int getInstanceMemoryLimit() {
        return instanceMemoryLimit;
    }

    public void setInstanceMemoryLimit(int instanceMemoryLimit) {
        this.instanceMemoryLimit = instanceMemoryLimit;
    }
}
