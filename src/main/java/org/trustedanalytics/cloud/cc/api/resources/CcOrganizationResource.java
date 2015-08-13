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
package org.trustedanalytics.cloud.cc.api.resources;

import org.trustedanalytics.cloud.cc.api.CcOrg;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.manageusers.CcUsersList;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.net.URI;
import java.util.UUID;

@Headers("Accept: application/json")
public interface CcOrganizationResource {

    @RequestLine("POST /v2/organizations")
    @Headers("Content-Type: application/json")
    @Body("%7B\"name\":\"{name}\"%7D")
    CcOrg createOrganization(@Param("name") String name);

    @RequestLine("PUT /v2/organizations/{org}")
    @Body("%7B\"name\":\"{name}\"%7D")
    void updateOrganization(@Param("org") UUID org, @Param("name") String name);

    @RequestLine("DELETE /v2/organizations/{org}")
    void deleteOrganization(@Param("org") UUID org);

    @RequestLine("GET /v2/organizations/{org}")
    CcOrg getOrganization(@Param("org") UUID org);

    @RequestLine("GET /v2/organizations")
    Page<CcOrg> getOrgs();

    @RequestLine(("GET"))
    Page<CcOrg> getOrgs(URI nextPageUrl);

    @RequestLine("GET /v2/organizations/{org}/{role}")
    CcUsersList getOrganizationUsers(@Param("org") UUID org, @Param("role") String role);

    @RequestLine("PUT /v2/organizations/{org}/users/{user}")
    void associateUserWithOrganization(@Param("org") UUID org, @Param("user") UUID user);

    @RequestLine("PUT /v2/organizations/{org}/managers/{manager}")
    void associateManagerWithOrganization(@Param("org") UUID org, @Param("manager") UUID manager);

    @RequestLine("PUT /v2/organizations/{org}/{role}/{user}")
    void associateUserWithOrganizationRole(@Param("org") UUID org, @Param("user") UUID user, @Param("role") String role);

    @RequestLine("DELETE /v2/organizations/{org}/{role}/{user}")
    void removeOrganizationRoleFromUser(@Param("org") UUID org, @Param("user") UUID user, @Param("role") String role);

    @RequestLine("GET /v2/organizations/{org}/spaces?inline-relations-depth=1")
    String getSpacesForOrganization(@Param("org") UUID org);
}
