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
package org.trustedanalytics.cloud.uaa;

import static java.util.stream.Collectors.joining;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import org.trustedanalytics.org.cloudfoundry.identity.uaa.scim.ScimUser;
import org.trustedanalytics.org.cloudfoundry.identity.uaa.scim.ScimUserFactory;
import org.trustedanalytics.org.cloudfoundry.identity.uaa.scim.SearchResults;

public class UaaClient implements UaaOperations {

    private final String uaaBaseUrl;

    private final RestOperations uaaRestTemplate;

    public UaaClient(RestOperations uaaRestTemplate, String uaaBaseUrl) {
        this.uaaBaseUrl = uaaBaseUrl;
        this.uaaRestTemplate = uaaRestTemplate;
    }

    @Override
    public ScimUser createUser(String username, String password) {
        return uaaRestTemplate
            .postForObject(uaaBaseUrl + "/Users", ScimUserFactory.newUser(username,
                password), ScimUser.class);
    }

    @Override
    public SearchResults<ScimUser> getUsers() {
        ResponseEntity<SearchResults<ScimUser>> result = uaaRestTemplate.exchange(
            uaaBaseUrl + "/Users",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<SearchResults<ScimUser>>() {
            });

        return result.getBody();
    }

    @Override
    public void deleteUser(UUID userGuid) {
        uaaRestTemplate.delete(uaaBaseUrl + "/Users/{id}", userGuid.toString());
    }
    
    @Override
    public Collection<UserIdNamePair> findUserNames(Collection<UUID> users) {
        String filter = users.stream()
                .map(uuid -> "Id eq \"" + uuid + "\"")
                .collect(joining(" or "));
        
        String path = uaaBaseUrl + "/Users?attributes=id,userName&filter=" + filter;
        return uaaRestTemplate.getForObject(path, UserIdNameList.class).getUsers();
    }
    
    @Override
    public void changePassword(UUID guid, ChangePasswordRequest request) {
        uaaRestTemplate.put(uaaBaseUrl + "/Users/{id}/password", request, guid);
    }

    @Override
    public Optional<UserIdNamePair> findUserIdByName(String userName) {
        String query = "/Users?attributes=id,userName&filter=userName eq '{name}'";
        Map<String, Object> pathVars = ImmutableMap.of("name", userName);
        UserIdNameList result = uaaRestTemplate.getForObject(uaaBaseUrl + query, UserIdNameList.class, pathVars);
        return Optional.ofNullable(Iterables.getFirst(result.getUsers(), null));
    }
}
