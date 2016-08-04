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
package org.trustedanalytics.cloud.cc.api.queries;

/**
 * List of all available filters for all request. Please note
 * that one filter is not appropriate for all requests. Refer
 * to http://apidocs.cloudfoundry.org/211/ for list of valid
 * filter for given request.
 */
public enum Filter {
    ACTIVE("active"),
    APP_GUID("app_guid"),
    DEVELOPER_GUID("developer_guid"),
    DOMAIN_GUID("domain_guid"),
    HOST("host"),
    LABEL("label"),
    NAME("name"),
    ORGANIZATION_GUID("organization_guid"),
    PATH("path"),
    PROVIDER("provider"),
    SERVICE_BROKER_GUID("service_broker_guid"),
    SERVICE_INSTANCE_GUID("service_instance_guid"),
    SERVICE_PLAN_GUID("service_plan_guid"),
    SPACE_GUID("space_guid");

    private final String name;

    Filter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
