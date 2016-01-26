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
package org.trustedanalytics.cloud.cc.api.manageusers;

import rx.Observable;

import java.util.Collection;
import java.util.UUID;

public interface CcOperationsUsers {

    /**
     * Returns all users count
     * @return users count
     */
    Observable<Integer> getUsersCount();

    /**
     * Returns all users
     * @return users
     */
    Observable<CcUser> getUsers();

    /**
     * Returns all users with given role within organization identified by given GUID.
     * @param orgGuid organization GUID
     * @param role user role
     * @return users within organization with specified role
     */
    Collection<User> getOrgUsers(UUID orgGuid, Role role);

    /**
     * Returns all users with given role within space identified by given GUID.
     * @param spaceGuid space GUID
     * @param role user role
     * @return users within space with specified role
     */
    Collection<User> getSpaceUsers(UUID spaceGuid, Role role);

    /**
     * Returns all users with their roles within space identified by given GUID
     * @param spaceGuid space GUID
     * @return users with their roles within space
     */
    Observable<User> getSpaceUsersWithRoles(UUID spaceGuid);

    /**
     * Returns all users with their roles within organization identified by given GUID
     * @param orgGuid organization GUID
     * @return users with their roles within organization
     */
    Observable<User> getOrgUsersWithRoles(UUID orgGuid);

    /**
     * Assigns organization role to user identified by given GUID.
     * @param userGuid user GUID
     * @param orgGuid organization GUID
     * @param role organization role
     */
    void assignOrgRole(UUID userGuid, UUID orgGuid, Role role);

    /**
     * Assigns space role to user identified by given GUID.
     * @param userGuid user GUID
     * @param orgGuid organization GUID
     * @param role space role
     */
    void assignSpaceRole(UUID userGuid, UUID orgGuid, Role role);

    /**
     * Deletes user identified by given GUID.
     * @param guid user GUID
     */
    void deleteUser(UUID guid);

    /**
     * Removes organization role from user identified by given GUID
     * @param userGuid user GUID
     * @param orgId organization GUID
     * @param role organization role
     */
    void revokeOrgRole(UUID userGuid, UUID orgId, Role role);

    /**
     * Removes space role from user identified by given GUID
     * @param userGuid user GUID
     * @param spaceId space GUID
     * @param role role
     */
    void revokeSpaceRole(UUID userGuid, UUID spaceId, Role role);
}
