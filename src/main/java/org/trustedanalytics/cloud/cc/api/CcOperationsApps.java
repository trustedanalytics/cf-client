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

public interface CcOperationsApps extends CcOperationsCommon {
    /**
     * Returns summary for application identified by given GUID.
     * @param app application GUID
     * @return application summary
     */
    CcAppSummary getAppSummary(UUID app);

    /**
     * Restages application identified by given GUID.
     * @param appGuid application GUID
     */
    void restageApp(UUID appGuid);

    /**
     * Returns all bindings belonging to application identified by given GUID.
     * @param app application GUID
     * @return bindings for given application
     */
    CcServiceBindingList getAppBindings(UUID app);

    /**
     * Returns all bindings belonging to application identified by given GUID and filtering by
     * given filter.
     * @param app application GUID
     * @param filterQuery filter
     * @return binding for given application matching filter
     */
    CcServiceBindingList getAppBindings(UUID app, FilterQuery filterQuery);

    /**
     * Deletes application identified by given GUID. All services bindings belonging to this
     * application will also be removed.
     * @param app application GUID
     */
    void deleteApp(UUID app);

    /**
     * Switches off application identified by given GUID.
     * @param app application GUID
     */
    void switchApp(UUID app, CcAppStatus appStatus);

    /**
     * Creates service binding for application.
     * @param ccNewServiceBinding binding for application
     * @return binding for application
     */
    CcServiceBinding createServiceBinding(CcNewServiceBinding ccNewServiceBinding);

    /**
     * Deletes service binding for application identified by given GUID.
     * @param bindingGuid binding GUID
     */
    void deleteServiceBinding(UUID bindingGuid);

    /**
     * Returns environment variables for application identified by given GUID.
     * @param appGuid application GUID
     */
    Observable<CcAppEnv> getAppEnv(UUID appGuid);
}
