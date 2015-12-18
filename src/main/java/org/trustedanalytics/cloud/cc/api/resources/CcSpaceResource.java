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

import org.trustedanalytics.cloud.cc.api.CcExtendedService;
import org.trustedanalytics.cloud.cc.api.CcSpace;
import org.trustedanalytics.cloud.cc.api.CcSummary;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.manageusers.CcOrgUsersList;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.net.URI;
import java.util.UUID;

@Headers("Accept: application/json")
public interface CcSpaceResource {

    @RequestLine("POST /v2/spaces")
    @Headers("Content-Type: application/json")
    @Body("%7B\"organization_guid\":\"{org},\"name\":\"{name}\"%7D")
    CcSpace createSpace(@Param("org") UUID org, @Param("name") String name);

    @RequestLine("GET /v2/spaces/{space}")
    CcSpace getSpace(@Param("space") UUID space);

    @RequestLine("GET /v2/spaces")
    Page<CcSpace> getSpaces();

    @RequestLine("GET")
    Page<CcSpace> getSpaces(URI nextPageUrl);

    @RequestLine("GET /v2/spaces/{space}/{role}")
    CcOrgUsersList getSpaceUsers(@Param("space") UUID space, @Param("role") String role);

    @RequestLine("GET /v2/spaces/{space}/summary?inline-relations-depth=1")
    CcSummary getSpaceSummary(@Param("space") UUID space);

    @RequestLine("PUT /v2/spaces/{space}/developers/{developer}")
    void associateDeveloperWithSpace(@Param("space") UUID space, @Param("developer") UUID developer);

    @RequestLine("PUT /v2/spaces/{space}/managers/{manager}")
    void associateManagerWithSpace(@Param("space") UUID space, @Param("manager") UUID manager);

    @RequestLine("PUT /v2/spaces/{space}/{role}/{user}")
    void associateUserWithSpaceRole(@Param("space") UUID org, @Param("user") UUID user, @Param("role") String role);

    @RequestLine("DELETE /v2/spaces/{space}/{role}/{user}")
    void removeSpaceRoleFromUser(@Param("space") UUID space, @Param("user") UUID user, @Param("role") String role);

    @RequestLine("GET /v2/spaces/{space}/services?inline-relations-depth=1")
    Page<CcExtendedService> getServices(@Param("space") UUID space);

    @RequestLine("GET")
    Page<CcExtendedService> getServices(URI nextPageUrl);

    @RequestLine("DELETE /v2/spaces/{space}?async=true&recursive=true")
    String removeSpace(@Param("space") UUID space);
}
