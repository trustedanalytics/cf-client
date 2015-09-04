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

    @JsonProperty("long_Description")
    private String longDescription;

    @JsonProperty("version")
    private String version;

    @JsonProperty("info_url")
    private String infoUrl;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("bindable")
    private Boolean bindable;

    @JsonProperty("unique_id")
    private String uniqueId;

    @JsonProperty("extra")
    private String extra;

    @JsonProperty("tags")
    private Object tags;

    @JsonProperty("requires")
    private Object requires;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    @JsonProperty("service_broker_guid")
    private UUID serviceBrokerGuid;

    @JsonProperty("plan_updateable")
    private Boolean planUpdateable;

    @JsonProperty("service_plans_url")
    private String servicePlansUrl;

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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public UUID getServiceBrokerGuid() {
        return serviceBrokerGuid;
    }

    public void setServiceBrokerGuid(UUID serviceBrokerGuid) {
        this.serviceBrokerGuid = serviceBrokerGuid;
    }

    public Boolean getPlanUpdateable() {
        return planUpdateable;
    }

    public void setPlanUpdateable(Boolean planUpdateable) {
        this.planUpdateable = planUpdateable;
    }

    public String getServicePlansUrl() {
        return servicePlansUrl;
    }

    public void setServicePlansUrl(String servicePlansUrl) {
        this.servicePlansUrl = servicePlansUrl;
    }
}