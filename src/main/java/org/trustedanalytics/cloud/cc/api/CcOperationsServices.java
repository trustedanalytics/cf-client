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

import java.util.Map;
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
     * @return all service instances
     */
    Observable<CcExtendedServiceInstance> getExtendedServiceInstances();

    /**
     * @param filterQuery filter to use when requesting service instances
     * @return filtered service instances
     */
    Observable<CcExtendedServiceInstance> getExtendedServiceInstances(FilterQuery filterQuery);

    /**
     * @param depth how deep the relations should be resolved
     * @return all service instances
     */
    Observable<CcExtendedServiceInstance> getExtendedServiceInstances(int depth);

    /**
     * @param filterQuery filter to use when requesting service instances
     * @param depth how deep the relations should be resolved
     * @return filtered service instances
     */
    Observable<CcExtendedServiceInstance> getExtendedServiceInstances(FilterQuery filterQuery, int depth);

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

    /**
     * Returns a list of all service keys acessible to user
     * @return list of service keys
     */
    Observable<CcServiceKey> getServiceKeys();

    /**
     * Creates a new service key
     * @return Created service key
     */
    Observable<CcServiceKey> createServiceKey(CcNewServiceKey serviceKey);
}
