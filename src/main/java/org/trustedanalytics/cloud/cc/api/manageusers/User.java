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
package org.trustedanalytics.cloud.cc.api.manageusers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

public class User {
    private UUID guid;
    private String username;
    private List<Role> roles;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("org_guid")
    private UUID orgGuid;
    
    public User(String username, UUID guid, Role... roles) {
        this(username, guid, ImmutableList.copyOf(roles));
    }

    public User(String username, UUID guid, List<Role> roles) {
        this(username, guid, ImmutableList.copyOf(roles), null);
    }

    public User(String username, UUID guid, List<Role> roles, UUID orgGuid) {
        this.guid = guid;
        this.username = username;
        this.roles = ImmutableList.copyOf(roles);
        this.orgGuid = orgGuid;
    }


    public UUID getGuid() {
        return guid;
    }

    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User appendRole(Role role) {
        List<Role> userRoles = ImmutableList.<Role>builder().addAll(this.roles).add(role).build();
        return new User(username, guid, userRoles);
    }

    public UUID getOrgGuid() {
        return orgGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User other = (User) o;

        return Objects.equals(guid, other.guid)
            && Objects.equals(username, other.username)
            && Objects.equals(roles, other.roles)
            && Objects.equals(orgGuid, other.orgGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid, username, roles, orgGuid);
    }

    @Override
    public String toString() {
        return String.format("User [guid=%s, username=%s, roles=%s, orgGuid=%s]", guid, username, roles, orgGuid);
    }
    
}
