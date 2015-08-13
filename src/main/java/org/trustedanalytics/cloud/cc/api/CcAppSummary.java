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
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcAppSummary {

    @JsonProperty("guid")
    private UUID guid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("memory")
    private Long memory;

    @JsonProperty("instances")
    private Long instances;

    @JsonProperty("disk_quota")
    private Long diskQuota;

    @JsonProperty("space_guid")
    private UUID spaceGuid;

    @JsonProperty("stack_guid")
    private UUID stackGuid;

    @JsonProperty("state")
    private String state;

    @JsonProperty("command")
    private String command;

    @JsonProperty("buildpack")
    private String buildpack;

    @JsonProperty("health_check_timeout")
    private String healtCheckTimeout;

    @JsonProperty("environment_json")
    private Object environmentJson;

    @JsonProperty("detected_buildpack")
    private String detectedBuildpack;

    @JsonProperty("detected_buildpack_guid")
    private UUID detectedBuildpackGuid;

    @JsonProperty("package_state")
    private String packageState;

    @JsonProperty("package_updated_at")
    private String packageUpdatedAt;

    @JsonProperty("system_env_json")
    private Object systemEnvJson;

    @JsonProperty("staging_task_id")
    private String stagingTaskId;

    @JsonProperty("running_instances")
    private Long runningInstances;

    @JsonProperty("available_domain")
    private String availableDomain;

    @JsonProperty("routes")
    private Object routes;

    @JsonProperty("version")
    private String version;

    @JsonProperty("services")
    private Collection<CcServiceInstance> services;

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<CcServiceInstance> getServices() {
        return services;
    }

    public void setServices(Collection<CcServiceInstance> services) {
        this.services = services;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getInstances() {
        return instances;
    }

    public void setInstances(Long instances) {
        this.instances = instances;
    }

    public Long getDiskQuota() {
        return diskQuota;
    }

    public void setDiskQuota(Long diskQuota) {
        this.diskQuota = diskQuota;
    }

    public UUID getSpaceGuid() {
        return spaceGuid;
    }

    public void setSpaceGuid(UUID spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    public UUID getStackGuid() {
        return stackGuid;
    }

    public void setStackGuid(UUID stackGuid) {
        this.stackGuid = stackGuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getBuildpack() {
        return buildpack;
    }

    public void setBuildpack(String buildpack) {
        this.buildpack = buildpack;
    }

    public String getHealtCheckTimeout() {
        return healtCheckTimeout;
    }

    public void setHealtCheckTimeout(String healtCheckTimeout) {
        this.healtCheckTimeout = healtCheckTimeout;
    }

    public Object getEnvironmentJson() {
        return environmentJson;
    }

    public void setEnvironmentJson(Object environmentJson) {
        this.environmentJson = environmentJson;
    }

    public String getDetectedBuildpack() {
        return detectedBuildpack;
    }

    public void setDetectedBuildpack(String detectedBuildpack) {
        this.detectedBuildpack = detectedBuildpack;
    }

    public UUID getDetectedBuildpackGuid() {
        return detectedBuildpackGuid;
    }

    public void setDetectedBuildpackGuid(UUID detectedBuildpackGuid) {
        this.detectedBuildpackGuid = detectedBuildpackGuid;
    }

    public String getPackageState() {
        return packageState;
    }

    public void setPackageState(String packageState) {
        this.packageState = packageState;
    }

    public String getPackageUpdatedAt() {
        return packageUpdatedAt;
    }

    public void setPackageUpdatedAt(String packageUpdatedAt) {
        this.packageUpdatedAt = packageUpdatedAt;
    }

    public Object getSystemEnvJson() {
        return systemEnvJson;
    }

    public void setSystemEnvJson(Object systemEnvJson) {
        this.systemEnvJson = systemEnvJson;
    }

    public String getStagingTaskId() {
        return stagingTaskId;
    }

    public void setStagingTaskId(String stagingTaskId) {
        this.stagingTaskId = stagingTaskId;
    }

    public Long getRunningInstances() {
        return runningInstances;
    }

    public void setRunningInstances(Long runningInstances) {
        this.runningInstances = runningInstances;
    }

    public String getAvailableDomain() {
        return availableDomain;
    }

    public void setAvailableDomain(String availableDomain) {
        this.availableDomain = availableDomain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getRoutes() {
        return routes;
    }

    public void setRoutes(Object routes) {
        this.routes = routes;
    }
}
