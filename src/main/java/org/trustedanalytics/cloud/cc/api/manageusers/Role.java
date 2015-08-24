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

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.Set;


public enum Role {
    MANAGERS("managers"),
    BILLING_MANAGERS("billing_managers"),
    AUDITORS("auditors"),
    DEVELOPERS("developers"),
    USERS("users"); //every user has to be in that role along any other

    public static final Set<Role> ORG_ROLES = EnumSet.of(MANAGERS, BILLING_MANAGERS, AUDITORS, USERS);
    public static final Set<Role> SPACE_ROLES = EnumSet.of(AUDITORS, DEVELOPERS, MANAGERS);

    private final String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }

    @JsonValue
    public String getValue() {
        return roleName;
    }
}
