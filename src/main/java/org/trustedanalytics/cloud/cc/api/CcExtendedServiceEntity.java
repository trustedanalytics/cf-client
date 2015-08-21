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
public class CcExtendedServiceEntity {

    @JsonProperty("label")
    private String label;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("long_description")
    private String long_description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("info_url")
    private String info_url;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("bindable")
    private Boolean bindable;

    @JsonProperty("unique_id")
    private String unique_id;

    @JsonProperty("extra")
    private String extra;

    @JsonProperty("tags")
    private Object tags;

    @JsonProperty("requires")
    private Object requires;

    @JsonProperty("documentation_url")
    private String documentation_url;

    @JsonProperty("service_broker_guid")
    private UUID service_broker_guid;

    @JsonProperty("plan_updateable")
    private Boolean plan_updateable;

    @JsonProperty("service_plans_url")
    private String service_plans_url;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getBindable() {
        return bindable;
    }

    public void setBindable(Boolean bindable) {
        this.bindable = bindable;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public Object getRequires() {
        return requires;
    }

    public void setRequires(Object requires) {
        this.requires = requires;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }

    public UUID getService_broker_guid() {
        return service_broker_guid;
    }

    public void setService_broker_guid(UUID service_broker_guid) {
        this.service_broker_guid = service_broker_guid;
    }

    public Boolean getPlan_updateable() {
        return plan_updateable;
    }

    public void setPlan_updateable(Boolean plan_updateable) {
        this.plan_updateable = plan_updateable;
    }

    public String getService_plans_url() {
        return service_plans_url;
    }

    public void setService_plans_url(String service_plans_url) {
        this.service_plans_url = service_plans_url;
    }
}