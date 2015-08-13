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

import org.trustedanalytics.cloud.cc.api.CcOrgsList;
import org.trustedanalytics.cloud.cc.api.CcSpacesList;
import org.trustedanalytics.cloud.cc.api.manageusers.CcUser;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

@Headers("Accept: application/json")
public interface CcUserResource {
    @RequestLine("POST /v2/users")
    @Headers("Content-Type: application/json")
    @Body("%7B\"guid\":\"{user}\"%7D")
    CcUser createUser(@Param("user") UUID user);

    @RequestLine("GET /v2/users/{user}/{spaceType}?q=organization_guid:{org}")
    CcSpacesList getUserSpaces(@Param("user") UUID user, @Param("spaceType") String spaceType, @Param("org") UUID org);

    @RequestLine("GET /v2/users/{user}/managed_organizations")
    CcOrgsList getManagedOrganizations(@Param("user") UUID user);

    @RequestLine("GET /v2/users/{user}/audited_organizations")
    CcOrgsList getAuditedOrganizations(@Param("user") UUID user);

    @RequestLine("GET /v2/users/{user}/billing_managed_organizations")
    CcOrgsList getBillingManagedOrganizations(@Param("user") UUID user);

    @RequestLine("GET /v2/users/{user}/organizations")
    CcOrgsList getUserOrganizations(@Param("user") UUID user);

    @RequestLine("DELETE /v2/users/{user}")
    void deleteUser(@Param("user") UUID user);
}
