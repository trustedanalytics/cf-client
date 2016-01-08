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

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CcSpace {

    private CcMetadata metadata;
    private CcSpaceEntity entity;

    public CcSpace() {
    }

    public CcSpace(UUID guid, String name, UUID orgGuid) {
        CcMetadata meta = new CcMetadata();
        meta.setGuid(guid);
        metadata = meta;
        CcSpaceEntity ent = new CcSpaceEntity();
        ent.setName(name);
        ent.setOrgGuid(orgGuid);
        entity = ent;
    }

    public CcMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(CcMetadata metadata) {
        this.metadata = metadata;
    }

    public CcSpaceEntity getEntity() {
        return entity;
    }

    public void setEntity(CcSpaceEntity entity) {
        this.entity = entity;
    }

    @JsonIgnore
    public UUID getGuid() {
        Optional<CcSpace> space = Optional.of(this);
        return space.map(CcSpace::getMetadata).map(CcMetadata::getGuid).orElse(null);
    }

    @JsonIgnore
    public String getName() {
        Optional<CcSpace> space = Optional.of(this);
        return space.map(CcSpace::getEntity).map(CcSpaceEntity::getName).orElse(null);
    }

    @JsonIgnore
    public UUID getOrgGuid() {
        Optional<CcSpace> space = Optional.of(this);
        return space.map(CcSpace::getEntity).map(CcSpaceEntity::getOrgGuid).orElse(null);
    }
}
