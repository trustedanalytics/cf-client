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
package org.trustedanalytics.cloud.cc;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableMap;
import org.cloudfoundry.client.lib.util.CloudEntityResourceMapper;
import org.cloudfoundry.client.lib.util.JsonUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.trustedanalytics.cloud.cc.api.CcAppEnv;
import org.trustedanalytics.cloud.cc.api.CcAppStatus;
import org.trustedanalytics.cloud.cc.api.CcAppSummary;
import org.trustedanalytics.cloud.cc.api.CcBuildpack;
import org.trustedanalytics.cloud.cc.api.CcExtendedService;
import org.trustedanalytics.cloud.cc.api.CcExtendedServiceInstance;
import org.trustedanalytics.cloud.cc.api.CcExtendedServicePlan;
import org.trustedanalytics.cloud.cc.api.CcMemoryUsage;
import org.trustedanalytics.cloud.cc.api.CcNewServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcNewServiceInstance;
import org.trustedanalytics.cloud.cc.api.CcNewServiceKey;
import org.trustedanalytics.cloud.cc.api.CcOperations;
import org.trustedanalytics.cloud.cc.api.CcOrg;
import org.trustedanalytics.cloud.cc.api.CcOrgPermission;
import org.trustedanalytics.cloud.cc.api.CcOrgSummary;
import org.trustedanalytics.cloud.cc.api.CcOrgsList;
import org.trustedanalytics.cloud.cc.api.CcQuota;
import org.trustedanalytics.cloud.cc.api.CcServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcServiceBindingList;
import org.trustedanalytics.cloud.cc.api.CcServiceKey;
import org.trustedanalytics.cloud.cc.api.CcSpace;
import org.trustedanalytics.cloud.cc.api.CcSpacesList;
import org.trustedanalytics.cloud.cc.api.CcSummary;
import org.trustedanalytics.cloud.cc.api.Page;
import org.trustedanalytics.cloud.cc.api.manageusers.CcUser;
import org.trustedanalytics.cloud.cc.api.manageusers.CcOrgUsersList;
import org.trustedanalytics.cloud.cc.api.manageusers.Role;
import org.trustedanalytics.cloud.cc.api.manageusers.User;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;
import org.trustedanalytics.cloud.cc.api.CcPlanVisibility;
import org.trustedanalytics.cloud.cc.api.CcPlanVisibilityEntity;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Prefer using {@link FeignClient} implementation in case of
 * new projects. Consider upgrading existing projects that are
 * using this implementation.
 */


/**
 * @deprecated
 */
@Deprecated
public class CcClient implements CcOperations {

    private static final String GUID_MUST_BE_NOT_NULL = "GUID must be not null";
    private static final String SPACES = "spaces";
    private static final String SPACE = "space";
    private static final String ORGANIZATIONS = "organizations";
    private static final String URL_V2_ORGANIZATIONS_ORG =  "/v2/organizations/{org}";
    private static final String UNSUPPORTED_OPERATION_MSG = "Use: ";

    private static Map<Role, String> roleMap;

    static {
        roleMap = new HashMap<>();
        roleMap.put(Role.MANAGERS, "managed_spaces");
        roleMap.put(Role.AUDITORS, "audited_spaces");
        roleMap.put(Role.DEVELOPERS, SPACES);
    }

    private final String baseUrl;
    private final RestOperations template;

    public CcClient(RestOperations template, String baseUrl) {
        this.baseUrl = baseUrl;
        this.template = template;
    }

    @Override public void createUser(UUID userGuid) {
        if (userGuid == null) {
            throw new IllegalArgumentException("UserGuid must be not null");
        }

        Map<String, Object> serviceRequest = new HashMap<String, Object>();
        serviceRequest.put("guid", userGuid);
        template.postForObject(getUrl("/v2/users"), serviceRequest, String.class);
    }

    @Override public UUID createOrganization(String orgName) {
        if (orgName == null) {
            throw new IllegalArgumentException("OrgName must be not null");
        }

        Map<String, Object> orgRequest = new HashMap<>();
        orgRequest.put("name", orgName);

        return postAndGetGuid("/v2/organizations", orgRequest);
    }

    @Override public UUID createSpace(UUID orgGuid, String spaceName) {
        if (orgGuid == null || spaceName == null) {
            throw new IllegalArgumentException("OrgGuid and SpaceName must be not null");
        }

        Map<String, Object> spaceRequest = new HashMap<>();
        spaceRequest.put("name", spaceName);
        spaceRequest.put("organization_guid", orgGuid);

        return postAndGetGuid("/v2/spaces", spaceRequest);
    }

    @Override public void assignUserToOrganization(UUID userGuid, UUID orgGuid) {
        if (userGuid == null || orgGuid == null) {
            throw new IllegalArgumentException("UserGuid and OrgGuid must be not null");
        }

        String orgUsersPath = "/v2/organizations/{org}/users/{user}";
        String orgManagersPath = "/v2/organizations/{org}/managers/{user}";

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("org", orgGuid);
        pathVars.put("user", userGuid);

        template.put(getUrl(orgUsersPath), null, pathVars);
        template.put(getUrl(orgManagersPath), null, pathVars);
    }

    @Override public void assignUserToSpace(UUID userGuid, UUID spaceGuid) {
        if (userGuid == null || spaceGuid == null) {
            throw new IllegalArgumentException("UserGuid and SpaceGuid must be not null");
        }

        final String spaceManagersPath = "/v2/spaces/{space}/managers/{user}";
        final String spaceDevelopersPath = "/v2/spaces/{space}/developers/{user}";

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put(SPACE, spaceGuid);
        pathVars.put("user", userGuid);

        template.put(getUrl(spaceManagersPath), null, pathVars);
        template.put(getUrl(spaceDevelopersPath), null, pathVars);
    }

    @Override public Observable<CcOrg> getOrg(UUID org) {
        if (org == null) {
            throw new IllegalArgumentException("Org uuid must be not null");
        }
        String orgsPath = URL_V2_ORGANIZATIONS_ORG;
        Map<String, Object> pathVars = ImmutableMap.of("org", org.toString());
        return Observable.defer(() -> Observable.just(
            template.getForEntity(baseUrl + orgsPath, CcOrg.class, pathVars).getBody()));
    }

    @Override
    public Observable<CcOrg> getOrgs() {
        return Observable.defer(() ->
                concatPages(getForEntity(baseUrl + "/v2/organizations", new ParameterizedTypeReference<Page<CcOrg>>() {}),
                        nextUrl -> getForEntity(baseUrl + nextUrl, new ParameterizedTypeReference<Page<CcOrg>>() {})));
    }

    @Override
    public Observable<CcSpace> getSpaces() {
        return Observable.defer(() ->
                concatPages(getForEntity(baseUrl + "/v2/spaces", new ParameterizedTypeReference<Page<CcSpace>>() {}),
                        nextUrl -> getForEntity(baseUrl + nextUrl, new ParameterizedTypeReference<Page<CcSpace>>() {})));
    }

    private <T> Observable<T> concatPages(Page<T> page, Function<String, Page<T>> more) {
        if (page.getNextUrl() == null) {
            return Observable.from(page.getResources());
        } else {
            return Observable.from(page.getResources())
                    .concatWith(Observable.defer(() -> concatPages(more.apply(page.getNextUrl()), more)));
        }
    }

    private <T> Page<T> getForEntity(String url, ParameterizedTypeReference<Page<T>> parameterizedTypeReference) {
        return template.exchange(url, HttpMethod.GET, null, parameterizedTypeReference).getBody();
    }

    private <T> Page<T> getForEntity(String url, ParameterizedTypeReference<Page<T>> parameterizedTypeReference, Map<String, Object> pathVars) {
        return template.exchange(url, HttpMethod.GET, null, parameterizedTypeReference, pathVars).getBody();
    }

    @Override public Observable<CcSpace> getSpace(UUID spaceId) {
        if (spaceId == null) {
            throw new IllegalArgumentException("spaceId uuid must be not null");
        }
        String spacePath = "/v2/spaces/{space}";
        Map<String, Object> pathVars = ImmutableMap.of(SPACE, spaceId.toString());
        return Observable.defer(() -> Observable
                .just(template.getForEntity(baseUrl + spacePath, CcSpace.class, pathVars).getBody()));
    }

    @Override public Observable<CcSpace> getSpaces(UUID org) {
        String spacesPath = "/v2/organizations/{org}/spaces?inline-relations-depth=1";
        Map<String, Object> pathVars = ImmutableMap.of("org", org.toString());
        return Observable.defer(() ->
            concatPages(
                    getForEntity(baseUrl + spacesPath, new ParameterizedTypeReference<Page<CcSpace>>() {
                    }, pathVars),
                    nextUrl -> getForEntity(baseUrl + nextUrl,
                            new ParameterizedTypeReference<Page<CcSpace>>() {
                            }, pathVars)));
    }

    @Override public Collection<CcOrg> getManagedOrganizations(UUID userId) {
        String managedOrgsPath = "/v2/users/{userId}/managed_organizations";
        return template.getForObject(baseUrl + managedOrgsPath, CcOrgsList.class, userId).getOrgs();
    }

    @Override public Collection<CcOrg> getAuditedOrganizations(UUID userId) {
        String auditedOrgsPath = "/v2/users/{userId}/audited_organizations";
        return template.getForObject(baseUrl + auditedOrgsPath, CcOrgsList.class, userId).getOrgs();
    }

    @Override public Collection<CcOrg> getBillingManagedOrganizations(UUID userId) {
        String billedOrgsPath = "/v2/users/{userId}/billing_managed_organizations";
        return template.getForObject(baseUrl + billedOrgsPath, CcOrgsList.class, userId).getOrgs();
    }

    @Override public Collection<CcOrgPermission> getUserPermissions(UUID user, Collection<UUID> orgsFilter) {

        Collection<CcOrg> orgs = getUserOrgs(user);
        if(!orgsFilter.isEmpty()) {
            orgs.removeIf(ccOrg -> !orgsFilter.contains(ccOrg.getGuid()));
        }

        Collection<CcOrg> managedOrganizations = getManagedOrganizations(user);
        Collection<CcOrg> auditedOrganizations = getAuditedOrganizations(user);
        Collection<CcOrg> billingManagedOrganizations = getBillingManagedOrganizations(user);

        Collection<CcOrgPermission> permissions = new ArrayList<>();
        orgs.forEach(org -> {
            boolean isManager = managedOrganizations.contains(org);
            boolean isAuditor = auditedOrganizations.contains(org);
            boolean isBillingManager = billingManagedOrganizations.contains(org);
            permissions.add(new CcOrgPermission(org, isManager, isAuditor, isBillingManager));
        });

        return permissions;
    }

    @Override public void renameOrg(UUID orgId, String name) {
        String renamePath = URL_V2_ORGANIZATIONS_ORG;
        Map<String, Object> pathVars = ImmutableMap.of("org", orgId.toString());
        Map<String, Object> body = ImmutableMap.of("name", name);
        template.put(baseUrl + renamePath, body, pathVars);
    }

    @Override public void deleteOrg(UUID orgGuid) {
        String renamePath = URL_V2_ORGANIZATIONS_ORG;
        Map<String, Object> pathVars = ImmutableMap.of("org", orgGuid.toString());
        template.delete(baseUrl + renamePath, pathVars);
    }

    @Override
    public void deleteSpace(UUID spaceGuid) {
        String removeSpacePath = "/v2/spaces/{space}?async=true&recursive=true";
        Map<String, Object> pathVars = ImmutableMap.of(SPACE, spaceGuid.toString());
        template.delete(baseUrl + removeSpacePath, pathVars);
    }

    @Override public Collection<CcSpace> getUsersSpaces(UUID userGuid, Role role, FilterQuery filterQuery) {
        String usersSpacesPath = "/v2/users/{user}/{spaceType}?q={query}";
        Map<String, Object> pathVars = ImmutableMap
            .of("user", userGuid.toString(), "spaceType", roleMap.get(role), "query", filterQuery.format());
        return template.getForEntity(baseUrl + usersSpacesPath, CcSpacesList.class, pathVars)
            .getBody().getSpaces();
    }

    private String getUrl(String url) {
        return baseUrl + url;
    }

    private UUID postAndGetGuid(String resourcePath, Map<String, Object> requestParams) {
        String resp = template.postForObject(getUrl(resourcePath), requestParams, String.class);
        Map<String, Object> respMap = JsonUtil.convertJsonToMap(resp);
        return new CloudEntityResourceMapper().getGuidOfResource(respMap);
    }

    @Override public CcSummary getSpaceSummary(UUID spaceGuid) {
        if (spaceGuid == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/spaces/{space}/summary?inline-relations-depth=1";
        Map<String, Object> pathVars = ImmutableMap.of(SPACE, spaceGuid.toString());
        return template.getForEntity(baseUrl + path, CcSummary.class, pathVars).getBody();
    }

    @Override public Observable<CcExtendedService> getServices(UUID spaceGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcExtendedService> getExtendedServices() {
        return Observable.defer(() ->
            concatPages(getForEntity(baseUrl + "/v2/services",
                new ParameterizedTypeReference<Page<CcExtendedService>>() {
                }),
                nextUrl -> getForEntity(baseUrl + nextUrl,
                    new ParameterizedTypeReference<Page<CcExtendedService>>() {
                    })));
    }

    @Override
    public Observable<CcExtendedServiceInstance> getExtendedServiceInstances() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public Observable<CcExtendedServiceInstance> getExtendedServiceInstances(FilterQuery filterQuery) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcExtendedServiceInstance> getExtendedServiceInstances(int depth) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcExtendedServiceInstance> getExtendedServiceInstances(
        FilterQuery filterQuery, int depth) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public Observable<CcMemoryUsage> getMemoryUsage(UUID orgGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcQuota> getQuota() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcBuildpack> getBuildpacks() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcOrgSummary> getOrgSummary(UUID orgGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcUser> getUsers() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getUsersCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getServicesCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getServiceInstancesCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getApplicationsCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getBuildpacksCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getSpacesCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<Integer> getOrgsCount() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public Observable<CcExtendedServicePlan> getExtendedServicePlans(UUID serviceGuid) {
        String spacesPath = "/v2/services/{service}/service_plans";
        Map<String, Object> pathVars = ImmutableMap.of("service", serviceGuid.toString());
        return Observable.defer(() ->
            concatPages(getForEntity(baseUrl + spacesPath,
                new ParameterizedTypeReference<Page<CcExtendedServicePlan>>() {
                }, pathVars),
                nextUrl -> getForEntity(baseUrl + nextUrl,
                    new ParameterizedTypeReference<Page<CcExtendedServicePlan>>() {
                    }, pathVars)));
    }

    @Override public Observable<CcExtendedService> getService(UUID serviceGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public CcServiceBindingList getServiceBindings(FilterQuery filterQuery) {
        String servicePath = "/v2/service_bindings?q={query}";
        Map<String, Object> pathVars = ImmutableMap.of("query", filterQuery.format());
        return template.getForEntity(baseUrl + servicePath, CcServiceBindingList.class, pathVars).getBody();
    }

    @Override public Observable<CcServiceKey> getServiceKeys() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcServiceKey> createServiceKey(CcNewServiceKey serviceKey) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public void deleteServiceKey(UUID keyGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public Observable<CcPlanVisibility> setExtendedServicePlanVisibility(UUID servicePlanGuid, UUID organizationGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public Observable<CcExtendedServiceInstance> createServiceInstance(
        CcNewServiceInstance serviceInstance) {

        if (serviceInstance == null || serviceInstance.getName() == null || serviceInstance.getPlanGuid() == null
            || serviceInstance.getSpaceGuid() == null || serviceInstance.getOrganizationGuid() == null) {
            throw new IllegalArgumentException("Name, PlanGuid, SpaceGuid and orgGuid must be not null");
        }

        String path = "/v2/service_instances";
        return Observable.defer(() ->
            Observable.just(template.postForEntity(baseUrl + path, serviceInstance, CcExtendedServiceInstance.class).getBody()));
    }

    @Override public void deleteServiceInstance(UUID instanceGuid) {
        if (instanceGuid == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/service_instances/{instance}";
        Map<String, String> pathVars = ImmutableMap.of("instance", instanceGuid.toString());
        template.delete(baseUrl + path, pathVars);
    }

    @Override public CcAppSummary getAppSummary(UUID app) {
        if (app == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }
        String path = "/v2/apps/{app}/summary";
        Map<String, Object> pathVars = ImmutableMap.of("app", app.toString());
        return template.getForEntity(baseUrl + path, CcAppSummary.class, pathVars).getBody();
    }

    @Override public void restageApp(UUID appGuid) {
        if (appGuid == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/apps/{app}/restage";
        Map<String, String> pathVars = ImmutableMap.of("app", appGuid.toString());
        template.postForEntity(baseUrl + path, null, String.class, pathVars);
    }

    @Override public CcServiceBindingList getAppBindings(UUID appGuid) {
        Objects.requireNonNull(appGuid);

        String appBindingsPath = "/v2/apps/{app}/service_bindings";
        Map<String, String> pathVars = ImmutableMap.of("app", appGuid.toString());
        return template.getForEntity(baseUrl + appBindingsPath, CcServiceBindingList.class, pathVars).getBody();
    }

    @Override
    public CcServiceBindingList getAppBindings(UUID app, FilterQuery filterQuery) {
        Objects.requireNonNull(app);
        Objects.requireNonNull(filterQuery);

        String appBindingsPath = "/v2/apps/{app}/service_bindings?q={query}";
        Map<String, String> pathVars = ImmutableMap.of("app", app.toString(), "query", filterQuery.format());
        return template.getForEntity(baseUrl + appBindingsPath, CcServiceBindingList.class, pathVars).getBody();
    }

    @Override public void deleteApp(UUID app) {
        if (app == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/apps/{app}?recursive=true";
        Map<String, String> pathVars = ImmutableMap.of("app", app.toString());
        template.delete(baseUrl + path, pathVars);
    }

    @Override public void switchApp(UUID app, CcAppStatus appStatus) {
        if (app == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/apps/{app}";
        Map<String, String> pathVars = ImmutableMap.of("app", app.toString());
        Map<String, Object> serviceRequest = new HashMap<String, Object>();
        serviceRequest.put("state", appStatus.getState());
        template.put(baseUrl + path, serviceRequest, pathVars);
    }

    @Override public CcServiceBinding createServiceBinding(CcNewServiceBinding ccNewServiceBinding) {
        if (ccNewServiceBinding == null || ccNewServiceBinding.getAppGuid() == null
            || ccNewServiceBinding.getServiceInstanceGuid() == null) {
            throw new IllegalArgumentException("AppGuid and ServiceInstanceGuid must be not null");
        }

        String path = "/v2/service_bindings";
        return template.postForEntity(baseUrl + path, ccNewServiceBinding, CcServiceBinding.class)
            .getBody();
    }

    @Override public void deleteServiceBinding(UUID bindingGuid) {
        if (bindingGuid == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        String path = "/v2/service_bindings/{binding}";
        Map<String, String> pathVars = ImmutableMap.of("binding", bindingGuid.toString());
        template.delete(baseUrl + path, pathVars);
    }

    @Override public Collection<CcOrg> getUserOrgs(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException(GUID_MUST_BE_NOT_NULL);
        }

        return template.getForObject(baseUrl + "/v2/users/{userId}/organizations", CcOrgsList.class, userId).getOrgs();
    }

    @Override public Collection<User> getOrgUsers(UUID orgGuid, Role role) {
        return getUsers(orgGuid, role, ORGANIZATIONS);
    }

    @Override public Collection<User> getSpaceUsers(UUID spaceGuid, Role role) {
        return getUsers(spaceGuid, role, SPACES);
    }

    @Override
    public Observable<User> getSpaceUsersWithRoles(UUID spaceGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override
    public Observable<User> getOrgUsersWithRoles(UUID orgGuid) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG + FeignClient.class.getName());
    }

    @Override public void deleteUser(UUID guid) {
        template.delete(baseUrl + "/v2/users/{guid}", guid);
    }

    @Override public void revokeOrgRole(UUID userGuid, UUID orgId, Role role) {
        revokeUserRole(ORGANIZATIONS, userGuid, orgId, role);
    }

    @Override public void revokeSpaceRole(UUID userGuid, UUID spaceId, Role role) {
        revokeUserRole(SPACES, userGuid, spaceId, role);
    }

    @Override public void assignOrgRole(UUID userGuid, UUID orgGuid, Role role) {
        assignUserRole(ORGANIZATIONS, userGuid, orgGuid, role);
    }

    @Override public void assignSpaceRole(UUID userGuid, UUID spaceGuid, Role role) {
        assignUserRole(SPACES, userGuid, spaceGuid, role);
    }

    @Override public Observable<CcAppEnv> getAppEnv(UUID appGuid) {
        Objects.requireNonNull(appGuid);
        String appEnvPath = "/v2/apps/{app}/env";
        Map<String, Object> pathVars = ImmutableMap.of("app", appGuid.toString());
        return Observable.defer(() -> Observable.just(new CcAppEnv(template.getForEntity(baseUrl + appEnvPath, CcAppEnv.class, pathVars).getBody())));
    }

    private Collection<User> getUsers(UUID guid, Role role, String type) {
        return template
            .getForObject(baseUrl + "/v2/" + type + "/{guid}/" + role.getValue(), CcOrgUsersList.class,
                guid).getUsers().stream().map(
                cfUser -> new User(cfUser.getUsername(), cfUser.getGuid(),
                    new ArrayList<>(asList(role)))).collect(toList());
    }

    private void revokeUserRole(String type, UUID userGuid, UUID orgSpaceGuid, Role role) {
        updateUserRole(type, userGuid, orgSpaceGuid, role, template::delete);
    }

    private void assignUserRole(String type, UUID userGuid, UUID orgSpaceGuid, Role role) {
        updateUserRole(type, userGuid, orgSpaceGuid, role,
            (url, pathVars) -> template.put(url, null, pathVars));
    }

    private void updateUserRole(String type, UUID userGuid, UUID orgSpaceGuid, Role role,
        BiConsumer<String, Map<String, Object>> runRequest) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("type", type);
        pathVars.put("user", userGuid);
        pathVars.put("orgSpace", orgSpaceGuid);
        pathVars.put("role", role.getValue());
        runRequest.accept(baseUrl + "/v2/{type}/{orgSpace}/{role}/{user}", pathVars);
    }
}
