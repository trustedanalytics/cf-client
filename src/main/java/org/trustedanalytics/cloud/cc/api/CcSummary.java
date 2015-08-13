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

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcSummary {

    private Collection<CcApp> apps;

    @JsonProperty("services")
    private Collection<CcServiceInstance> serviceInstances;

    public Collection<CcApp> getApps() {
        return apps;
    }

    public void setApps(Collection<CcApp> apps) {
        this.apps = apps;
    }

    public Collection<CcServiceInstance> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<CcServiceInstance> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CcSummary ccSummary = (CcSummary) o;

        if (apps != null ? !apps.equals(ccSummary.apps) : ccSummary.apps != null) {
            return false;
        }
        if (serviceInstances != null ?
            !serviceInstances.equals(ccSummary.serviceInstances) :
            ccSummary.serviceInstances != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = apps != null ? apps.hashCode() : 0;
        result = 31 * result + (serviceInstances != null ? serviceInstances.hashCode() : 0);
        return result;
    }
}
