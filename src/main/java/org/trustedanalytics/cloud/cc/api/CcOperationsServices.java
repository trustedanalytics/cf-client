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
    Observable<CcExtendedService> getServices(UUID spaceGuid);

    /**
     * Returns services within organization identified by given GUID
     * @param orgGuid GUID
     * @return services
     */
    Observable<CcExtendedService> getOrganizationServices(UUID orgGuid);

    /**
     * Returns services
     * @return services
     */
    Observable<CcExtendedService> getExtendedServices();

    /**
     * @param filterQuery filter to use when requesting services
     * @return filtered services
     */
    Observable<CcExtendedService> getExtendedServices(FilterQuery filterQuery);

    /**
     * Returns service plans within service identified by given GUID
     * @return services plans
     */
    Observable<CcExtendedServicePlan> getExtendedServicePlans(UUID serviceGuid);

    /**
     *  Get visibility of service plan to specific organization
     *  @param servicePlanGuid
     *  @return service plan identified by given service plan GUID
     */
    Observable<CcExtendedServicePlan> getExtendedServicePlan(UUID servicePlanGuid);

    /**
     * Returns service identified by given GUID.
     * @param serviceGuid GUID
     * @return service
     */
    Observable<CcExtendedService> getService(UUID serviceGuid);

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
     * @param instanceGuid GUID
     * @return service instance identified by given GUID
     */
    Observable<CcExtendedServiceInstance> getServiceInstance(UUID instanceGuid);


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

    /**
     * Deletes given service key
     */
    void deleteServiceKey(UUID keyGuid);

    Observable<Integer> getServicesCount();

    /**
     * Returns total number of services
     * @return total number of services
     */

    Observable<Integer> getServiceInstancesCount();

    /**
     * Returns total number of service instances
     * @return total number of service instances
     */

    /**
     * Set visibility of service plan to specific organization
     * @param servicePlanGuid service plan GUID
     * @param organizationGuid organization GUID
     * @return services plan visibility
     */
    Observable<CcPlanVisibility> setExtendedServicePlanVisibility(UUID servicePlanGuid, UUID organizationGuid);

    /**
     * Get visibility of service plan to specific organization
     * @param filterQuery filter
     * @return services plan visibility
     */
    Observable<CcPlanVisibility> getExtendedServicePlanVisibility(FilterQuery filterQuery);
}
