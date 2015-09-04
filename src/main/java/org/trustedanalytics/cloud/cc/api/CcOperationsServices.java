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

import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;
import rx.Observable;

import java.util.UUID;

public interface CcOperationsServices extends CcOperationsCommon {
    /**
     * Returns services within space identified by given GUID.
     * @param spaceGuid GUID
     * @return services
     */
    String getServices(UUID spaceGuid);

    /**
     * Returns services
     * @return services
     */
    Observable<CcExtendedService> getExtendedServices();

    /**
     * Returns service plans within service identified by given GUID
     * @return services plans
     */
    Observable<CcExtendedServicePlan> getExtendedServicePlans(UUID serviceGuid);

    /**
     * Returns service identified by given GUID.
     * @param serviceGuid GUID
     * @return service
     */
    String getService(UUID serviceGuid);

    /**
     * Creates new service instance using provided configuration.
     * @param serviceInstance service instance
     * @return new service instance
     */
    Observable<CcExtendedServiceInstance> createServiceInstance(CcNewServiceInstance serviceInstance);

    /**
     * Deletes service instance identified by given GUID.
     * @param instanceGuid GUID
     */
    void deleteServiceInstance(UUID instanceGuid);

    /**
     * Returns list of service bindings matching provided filter
     * @param filterQuery filter
     * @return matching service bindings
     */
    CcServiceBindingList getServiceBindings(FilterQuery filterQuery);
}
