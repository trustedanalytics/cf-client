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

import feign.Headers;
import feign.RequestLine;
import org.trustedanalytics.cloud.cc.api.CcQuota;
import org.trustedanalytics.cloud.cc.api.Page;

import java.net.URI;

@Headers("Accept: application/json")
public interface CcQuotaResource {

    @RequestLine("GET /v2/quota_definitions")
    Page<CcQuota> getQuota();

    @RequestLine("GET")
    Page<CcQuota> getQuota(URI nextPageUrl);
}
