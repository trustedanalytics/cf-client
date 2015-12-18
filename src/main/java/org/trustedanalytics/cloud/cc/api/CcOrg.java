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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;
import java.util.UUID;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcOrg {

    private CcMetadata metadata;
    private CcOrgEntity entity;

    public CcOrg() {
    }

    public CcOrg(UUID guid, String name) {
        CcMetadata meta = new CcMetadata();
        meta.setGuid(guid.toString());
        metadata = meta;
        CcOrgEntity ent = new CcOrgEntity();
        ent.setName(name);
        entity = ent;
    }

    public CcMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(CcMetadata metadata) {
        this.metadata = metadata;
    }

    public CcOrgEntity getEntity() {
        return entity;
    }

    public void setEntity(CcOrgEntity entity) {
        this.entity = entity;
    }

    @JsonIgnore
    public UUID getGuid() {
        Optional<CcOrg> space = Optional.of(this);
        return UUID.fromString(space.map(CcOrg::getMetadata).map(CcMetadata::getGuid).orElse(null));
    }

    @JsonIgnore
    public String getName() {
        Optional<CcOrg> org = Optional.of(this);
        return org.map(CcOrg::getEntity).map(CcOrgEntity::getName).orElse(null);
    }

    @JsonIgnore
    public String getStatus() {
        Optional<CcOrg> org = Optional.of(this);
        return org.map(CcOrg::getEntity).map(CcOrgEntity::getStatus).orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;
        return this.getGuid().equals(((CcOrg)other).getGuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getGuid());
    }
}
