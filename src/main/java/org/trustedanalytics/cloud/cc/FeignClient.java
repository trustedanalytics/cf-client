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

import org.trustedanalytics.cloud.cc.api.loggers.ScramblingSlf4jLogger;
import org.trustedanalytics.cloud.cc.api.manageusers.CcUsersList;
import org.trustedanalytics.cloud.cc.api.manageusers.Role;
import org.trustedanalytics.cloud.cc.api.manageusers.User;
import org.trustedanalytics.cloud.cc.api.queries.FilterQuery;
import org.trustedanalytics.cloud.cc.api.resources.CcApplicationResource;
import org.trustedanalytics.cloud.cc.api.resources.CcOrganizationResource;
import org.trustedanalytics.cloud.cc.api.resources.CcServiceBindingResource;
import org.trustedanalytics.cloud.cc.api.resources.CcServiceResource;
import org.trustedanalytics.cloud.cc.api.resources.CcSpaceResource;
import org.trustedanalytics.cloud.cc.api.resources.CcUserResource;

import com.google.common.collect.ImmutableMap;

import feign.Feign;
import feign.Feign.Builder;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import org.apache.commons.lang.StringUtils;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.trustedanalytics.cloud.cc.api.*;
import rx.Observable;

public class FeignClient implements CcOperations {
    private static final Map<Role, String> ROLE_MAP = ImmutableMap.<Role, String>builder()
        .put(Role.MANAGERS, "managed_spaces")
        .put(Role.AUDITORS, "audited_spaces")
        .put(Role.DEVELOPERS,"spaces")
        .build();

    private static final int CONNECT_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(30);
    private static final int READ_TIMEOUT = (int) TimeUnit.MINUTES.toMillis(5);

    // We do a lot of delegation here because of https://github.com/Netflix/feign/issues/133
    private final CcApplicationResource applicationResource;
    private final CcOrganizationResource organizationResource;
    private final CcServiceResource serviceResource;
    private final CcServiceBindingResource serviceBindingResource;
    private final CcSpaceResource spaceResource;
    private final CcUserResource userResource;

    /**
     * Creates client applying default configuration
     * @param url endpoint url
     */
    public FeignClient(String url) {
        this(url, Function.identity());
    }

    /**
     * Creates client applying default configuration and then customizations. Example:
     * <pre>
     * {@code
     * new FeignClient(apiUrl, builder -> builder.requestInterceptor(template ->
     * template.header("Authorization", "bearer " + token)));
     * }
     * </pre>
     * @param url endpoint url
     * @param customizations custom configuration that should be applied after defaults
     */
    public FeignClient(String url, Function<Builder, Builder> customizations) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(customizations);

        // avoid duplication of slashes
        final String targetUrl = StringUtils.removeEnd(url, "/");

        // first applies defaults and then custom configuration
        final Builder builder = customizations.apply(Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(CONNECT_TIMEOUT, READ_TIMEOUT))
                .logger(new ScramblingSlf4jLogger(FeignClient.class))
                .logLevel(feign.Logger.Level.FULL));

        this.applicationResource = builder.target(CcApplicationResource.class, targetUrl);
        this.organizationResource = builder.target(CcOrganizationResource.class, targetUrl);
        this.serviceResource = builder.target(CcServiceResource.class, targetUrl);
        this.serviceBindingResource = builder.target(CcServiceBindingResource.class, targetUrl);
        this.spaceResource = builder.target(CcSpaceResource.class, targetUrl);
        this.userResource = builder.target(CcUserResource.class, targetUrl);
    }

    @Override public CcAppSummary getAppSummary(UUID app) {
        return applicationResource.getAppSummary(app);
    }

    @Override public void restageApp(UUID appGuid) {
        applicationResource.restageApp(appGuid);
    }

    @Override public CcServiceBindingList getAppBindings(UUID app) {
        return applicationResource.getAppBindings(app);
    }

    @Override
    public CcServiceBindingList getAppBindings(UUID app, FilterQuery query) {
        return applicationResource.getAppBindings(app, query);
    }

    @Override public void deleteApp(UUID app) {
        applicationResource.deleteApp(app);
    }

    @Override public CcServiceBinding createServiceBinding(CcNewServiceBinding ccNewServiceBinding) {
        return serviceBindingResource.createServiceBinding(ccNewServiceBinding);
    }

    @Override public void deleteServiceBinding(UUID bindingGuid) {
        serviceBindingResource.deleteServiceBinding(bindingGuid);
    }

    @Override public void createUser(UUID userGuid) {
        userResource.createUser(userGuid);
    }

    @Override public UUID createOrganization(String orgName) {
        return organizationResource.createOrganization(orgName).getGuid();
    }

    @Override public UUID createSpace(UUID orgGuid, String name) {
        return spaceResource.createSpace(orgGuid, name).getGuid();
    }

    @Override public void assignUserToOrganization(UUID userGuid, UUID orgGuid) {
        organizationResource.associateUserWithOrganization(orgGuid, userGuid);
        organizationResource.associateManagerWithOrganization(orgGuid, userGuid);
    }

    @Override public void assignUserToSpace(UUID userGuid, UUID spaceGuid) {
        spaceResource.associateDeveloperWithSpace(spaceGuid, userGuid);
        spaceResource.associateManagerWithSpace(spaceGuid, userGuid);
    }

    @Override public Observable<CcOrg> getOrg(UUID orgUUID) {
        return Observable.defer(() -> Observable.just(organizationResource.getOrganization(orgUUID)));
    }

    @Override
    public Observable<CcOrg> getOrgs() {
        return Observable.defer(() -> concatPages(organizationResource.getOrgs(), organizationResource::getOrgs));
    }

    @Override
    public Observable<CcSpace> getSpaces() {
        return Observable.defer(() -> concatPages(spaceResource.getSpaces(), spaceResource::getSpaces));
    }

    private <T> Observable<T> concatPages(Page<T> page, Function<URI , Page<T>> more) {
        if (page.getNextUrl() == null) {
            return Observable.from(page.getResources());
        } else {
            final URI nextUrl = URI.create(page.getNextUrl());
            return Observable.from(page.getResources())
                    .concatWith(Observable.defer(() -> concatPages(more.apply(nextUrl), more)));
        }
    }

    @Override public Observable<CcSpace> getSpace(UUID spaceId) {
        return Observable.defer(() -> Observable.just(spaceResource.getSpace(spaceId)));
    }

    @Override public Observable<CcSpace> getSpaces(UUID org) {
        return Observable.defer(() -> concatPages(organizationResource.getSpacesForOrganization(org), spaceResource::getSpaces));
    }

    @Override public Collection<CcOrg> getManagedOrganizations(UUID user) {
        return userResource.getManagedOrganizations(user).getOrgs();
    }

    @Override public Collection<CcOrg> getAuditedOrganizations(UUID user) {
        return userResource.getAuditedOrganizations(user).getOrgs();
    }

    @Override public Collection<CcOrg> getBillingManagedOrganizations(UUID user) {
        return userResource.getBillingManagedOrganizations(user).getOrgs();
    }

    @Override public void renameOrg(UUID orgId, String name) {
        organizationResource.updateOrganization(orgId, name);
    }

    @Override public void deleteOrg(UUID orgGuid) {
        organizationResource.deleteOrganization(orgGuid);
    }

    @Override
    public void deleteSpace(UUID spaceGuid) {
        spaceResource.removeSpace(spaceGuid);
    }

    @Override public Collection<CcSpace> getUsersSpaces(UUID userGuid, Role role, UUID orgGuid) {
        return userResource.getUserSpaces(userGuid, ROLE_MAP.get(role), orgGuid).getSpaces();
    }

    @Override
    public Observable<CcExtendedService> getExtendedServices() {
        return Observable.defer(() -> concatPages(serviceResource.getServices(), serviceResource::getServices));
    }

    @Override
    public Observable<CcExtendedServiceInstance> getExtendedServiceInstances() {
        return Observable.defer(() -> concatPages(serviceResource.getExtendedServiceInstances(),
            serviceResource::getExtendedServiceInstances));
    }

    @Override
    public Observable<CcExtendedServiceInstance> getExtendedServiceInstances(FilterQuery filterQuery) {
        return Observable.defer(() -> concatPages(serviceResource.getExtendedServiceInstances(filterQuery),
            serviceResource::getExtendedServiceInstances));
    }

    @Override
    public Observable<CcExtendedServicePlan> getExtendedServicePlans(UUID serviceGuid) {
        return Observable.defer(() -> concatPages(serviceResource.getExtendedServicePlans(serviceGuid),
            serviceResource::getExtendedServicePlans));
    }

    @Override public String getServices(UUID spaceGuid) {
        return spaceResource.getServices(spaceGuid);
    }

    @Override public String getService(UUID serviceGuid) {
        return serviceResource.getService(serviceGuid);
    }

    @Override
    public CcServiceBindingList getServiceBindings(FilterQuery filterQuery) {
        return serviceBindingResource.getServiceBindings(filterQuery);
    }

    @Override public Observable<CcExtendedServiceInstance> createServiceInstance(CcNewServiceInstance serviceInstance) {
        return Observable.defer(() -> Observable.just(serviceResource.createServiceInstance(serviceInstance)));
    }

    @Override public void deleteServiceInstance(UUID instanceGuid) {
        serviceResource.deleteServiceInstance(instanceGuid);
    }

    @Override public CcSummary getSpaceSummary(UUID spaceGuid) {
        return spaceResource.getSpaceSummary(spaceGuid);
    }

    @Override public Collection<CcOrg> getUserOrgs(UUID userGuid) {
        return userResource.getUserOrganizations(userGuid).getOrgs();
    }

    @Override public Collection<CcOrgPermission> getUserPermissions(UUID user, Collection<UUID> orgsFilter) {
        Collection<CcOrg> orgs = userResource.getUserOrganizations(user).getOrgs();

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

    @Override public Collection<User> getOrgUsers(UUID orgGuid, Role role) {
        return toUsers(organizationResource.getOrganizationUsers(orgGuid, role.getValue()), role);
    }

    @Override public Collection<User> getSpaceUsers(UUID spaceGuid, Role role) {
        return toUsers(spaceResource.getSpaceUsers(spaceGuid, role.getValue()), role);
    }

    @Override public void assignOrgRole(UUID userGuid, UUID orgGuid, Role role) {
        organizationResource.associateUserWithOrganizationRole(orgGuid, userGuid, role.getValue());
    }

    @Override public void assignSpaceRole(UUID userGuid, UUID orgGuid, Role role) {
        spaceResource.associateUserWithSpaceRole(orgGuid, userGuid, role.getValue());
    }

    @Override public void deleteUser(UUID guid) {
        userResource.deleteUser(guid);
    }

    @Override public void revokeOrgRole(UUID userGuid, UUID orgId, Role role) {
        organizationResource.removeOrganizationRoleFromUser(orgId, userGuid, role.getValue());
    }

    @Override public void revokeSpaceRole(UUID userGuid, UUID spaceId, Role role) {
        spaceResource.removeSpaceRoleFromUser(spaceId, userGuid, role.getValue());
    }

    private Collection<User> toUsers(CcUsersList ccUsers, Role role) {
        return ccUsers.getUsers().stream()
            .map(ccUser -> new User(ccUser.getUsername(), ccUser.getGuid(), role))
            .collect(Collectors.toList());
    }

    @Override public void switchApp(UUID app, CcAppStatus appStatus) {
        applicationResource.switchApp(app, appStatus);
    }

    @Override public Observable<CcAppEnv> getAppEnv(UUID appUUID) {
        return Observable.defer(() -> Observable.just(new CcAppEnv(applicationResource.getAppEnv(appUUID))));
    }
}
