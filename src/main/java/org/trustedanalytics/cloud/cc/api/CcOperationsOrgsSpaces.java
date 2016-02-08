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

import org.trustedanalytics.cloud.cc.api.manageusers.Role;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;
import rx.Observable;

import java.util.Collection;
import java.util.UUID;

public interface CcOperationsOrgsSpaces {

    /**
     * Creates new user with given GUID.
     * @param userGuid user GUID
     */
    void createUser(UUID userGuid);

    /**
     * Creates organization with given name.
     * @param orgName name of organization
     * @return UUID of created organization
     */
    UUID createOrganization(String orgName);

    /**
     * Creates space within given organization.
     * @param orgGuid organization GUID
     * @param name space name
     * @return space GUID
     */
    UUID createSpace(UUID orgGuid, String name);

    /**
     * Assigns user identified by given GUID to organization identified by given GUID.
     * @param userGuid user GUID
     * @param orgGuid organization GUID
     */
    void assignUserToOrganization(UUID userGuid, UUID orgGuid);

    /**
     * Assigns user identified by given GUID to space identified by given GUID.
     * @param userGuid user GUID
     * @param spaceGuid space GUID
     */
    void assignUserToSpace(UUID userGuid, UUID spaceGuid);

    /**
     * Returns organization identified by given GUID.
     * @param orgUUID organization GUID
     * @return organization
     */
    Observable<CcOrg> getOrg(UUID orgUUID);

    /**
     * Returns all organizations.
     * @return organizations
     */
    Observable<CcOrg> getOrgs();

    /**
     * Returns all spaces.
     * @return spaces
     */
    Observable<CcSpace> getSpaces();

    /**
     * Returns space identified by given GUID.
     * @param spaceId space GUID
     * @return space
     */
    Observable<CcSpace> getSpace(UUID spaceId);

    /**
     * Returns all spaces withing organization identified by given GUID.
     * @param org GUID
     * @return spaces
     */
    Observable<CcSpace> getSpaces(UUID org);

    /**
     * Returns organizations managed by user identified by given GUID.
     * @param user GUID
     * @return organizations
     */
    Collection<CcOrg> getManagedOrganizations(UUID user);

    /**
     * Returns organizations audited by user identified by given GUID.
     * @param user GUID
     * @return organizations
     */
    Collection<CcOrg> getAuditedOrganizations(UUID user);

    /**
     * Returns organizations billed by user identified by given GUID.
     * @param user GUID
     * @return organizations
     */
    Collection<CcOrg> getBillingManagedOrganizations(UUID user);

    /**
     * Returns organizations assigned to user identified by given GUID.
     * @param user GUID
     * @return organizations
     */
    Collection<CcOrg> getUserOrgs(UUID user);

    /**
     * Returns users permissions for given organizations.
     * @param user GUID
     * @param orgIDs organizations UUIDs
     * @return permissions in organizations
     */
    Collection<CcOrgPermission> getUserPermissions(UUID user, Collection<UUID> orgIDs);

    /**
     * Renames organization identified by given GUID.
     * @param orgId GUID
     * @param name new name
     */
    void renameOrg(UUID orgId, String name);

    /**
     * Deletes organization identified by given GUID.
     * @param orgGuid GUID
     */
    void deleteOrg(UUID orgGuid);

    /**
     * Deletes space identified by given GUID.
     * @param spaceGuid GUID
     */
    void deleteSpace(UUID spaceGuid);

    /**
     * Get spaces that user has access to in given role.
     * @param userGuid GUID
     * @param role user role
     * @param filterQuery filter
     * @return spaces
     */
    Collection<CcSpace> getUsersSpaces(UUID userGuid, Role role, FilterQuery filterQuery);

    /**
     * Get memory usage from organization identified by given GUID.
     * @param orgGuid GUID
     */
    Observable<CcMemoryUsage> getMemoryUsage(UUID orgGuid);

    /**
     * Get organization summary from organization identified by given GUID.
     * @param orgGuid GUID
     */
    Observable<CcOrgSummary> getOrgSummary(UUID orgGuid);

    /**
     * Returns total number of organizations.
     * @return total number of organizations
     */
    Observable<Integer> getOrgsCount();

    /**
     * Returns total number of spaces.
     * @return total number of spaces
     */
    Observable<Integer> getSpacesCount();
}
