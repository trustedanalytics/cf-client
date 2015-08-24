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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CcNewServiceInstance {
    private String name;
    private UUID organizationGuid;
    private UUID spaceGuid;
    private UUID planGuid;
    private Map<String, Object> parameters;

    public CcNewServiceInstance() {
    }

    public CcNewServiceInstance(String name, UUID orgGuid, UUID spaceGuid, UUID planGuid) {
        this.name = name;
        this.organizationGuid = orgGuid;
        this.spaceGuid = spaceGuid;
        this.planGuid = planGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("organization_guid")
    public UUID getOrganizationGuid() {
        return organizationGuid;
    }

    @JsonProperty("space_guid")
    public UUID getSpaceGuid() {
        return spaceGuid;
    }

    @JsonProperty("service_plan_guid")
    public UUID getPlanGuid() {
        return planGuid;
    }

    @JsonProperty("organization_guid")
    public void setOrganizationGuid(UUID organizationGuid) {
        this.organizationGuid = organizationGuid;
    }

    @JsonProperty("space_guid")
    public void setSpaceGuid(UUID spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    @JsonProperty("service_plan_guid")
    public void setPlanGuid(UUID planGuid) {
        this.planGuid = planGuid;
    }

    @JsonProperty("parameters")
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CcNewServiceInstance that = (CcNewServiceInstance) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (planGuid != null ? !planGuid.equals(that.planGuid) : that.planGuid != null) {
            return false;
        }
        if (spaceGuid != null ? !spaceGuid.equals(that.spaceGuid) : that.spaceGuid != null) {
            return false;
        }
        if (organizationGuid != null ? !organizationGuid.equals(that.organizationGuid) : that.organizationGuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (spaceGuid != null ? spaceGuid.hashCode() : 0);
        result = 31 * result + (planGuid != null ? planGuid.hashCode() : 0);
        result = 31 * result + (organizationGuid != null ? organizationGuid.hashCode() : 0);
        return result;
    }
}
