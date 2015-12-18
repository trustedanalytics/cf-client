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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.trustedanalytics.cloud.cc.api.CcAppSummary;
import org.trustedanalytics.cloud.cc.api.CcNewServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcNewServiceInstance;
import org.trustedanalytics.cloud.cc.api.CcOrg;
import org.trustedanalytics.cloud.cc.api.CcOrgsList;
import org.trustedanalytics.cloud.cc.api.CcServiceBinding;
import org.trustedanalytics.cloud.cc.api.CcSpace;
import org.trustedanalytics.cloud.cc.api.CcSummary;
import org.trustedanalytics.cloud.cc.api.CcAppState;
import org.trustedanalytics.cloud.cc.api.CcAppStatus;
import org.trustedanalytics.cloud.cc.api.manageusers.CcOrgUsersList;
import org.trustedanalytics.cloud.cc.api.manageusers.Role;
import org.trustedanalytics.cloud.cc.api.manageusers.User;
import org.trustedanalytics.cloud.cc.api.CcExtendedServiceInstance;

import com.google.common.base.Function;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import rx.Observable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RunWith(MockitoJUnitRunner.class) public class CcClientTest {

    private static final String BASE_URL = "base_url";
    @Mock private RestOperations template;
    private CcClient sut;

    @Before public void setup() {
        sut = new CcClient(template, BASE_URL);
    }

    @Test public void createUser_correctData_correctRestParams() {
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/users";
        sut.createUser(guid);

        verify(template).postForObject(eq(apiUrl), any(Map.class), eq(String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUser_emptyGUID_returnIllegalArgumentException() {
        sut.createUser(null);
    }

    private void mockTemplatePostForObject(String url, UUID guid) {
        when(template.postForObject(eq(url), any(Map.class), eq(String.class)))
            .thenReturn(createFakeJsonResponse(guid));
    }

    private String createFakeJsonResponse(UUID guid) {
        return "{\"metadata\":{\"guid\":\"" + guid.toString() + "\"}}";
    }

    @Test public void createOrganization_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/organizations";
        validatePostForObject(sut::createOrganization, apiUrl);
    }


    private void validatePostForObject(Function<String, UUID> fun, String apiUrl){
        String org_name = "";
        UUID guid = UUID.randomUUID();

        mockTemplatePostForObject(apiUrl, guid);

        UUID orgGuid = fun.apply(org_name);

        verify(template).postForObject(eq(apiUrl), any(Map.class), eq(String.class));
        assertEquals(guid, orgGuid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOrganization_emptyName_returnIllegalArgumentException() {
        sut.createOrganization(null);
    }

    @Test public void createSpace_correctData_correctRestParams() {
        String orgName = "";
        String apiUrl = BASE_URL + "/v2/spaces";
        UUID guid = UUID.randomUUID();

        mockTemplatePostForObject(apiUrl, guid);

        UUID orgGuid = sut.createSpace(guid, orgName);

        verify(template).postForObject(eq(apiUrl), any(Map.class), eq(String.class));
        assertEquals(guid, orgGuid);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createSpace_emptyName_returnIllegalArgumentException() {
        sut.createSpace(null, null);
    }

    @Test public void assignUserToOrganization_correctData_correctRestParams() {
        String apiUrl1 = BASE_URL + "/v2/organizations/{org}/users/{user}";
        String apiUrl2 = BASE_URL + "/v2/organizations/{org}/managers/{user}";

        verifyAddingUser(sut::assignUserToOrganization, apiUrl1, apiUrl2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assignUserToOrganization_emptyGUID_returnIllegalArgumentException() {
        sut.assignUserToOrganization(null, null);
    }

    @Test public void assignUserToSpace_correctData_correctRestParams() {
        String apiUrl1 = BASE_URL + "/v2/spaces/{space}/managers/{user}";
        String apiUrl2 = BASE_URL + "/v2/spaces/{space}/developers/{user}";

        verifyAddingUser(sut::assignUserToSpace, apiUrl1, apiUrl2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assignUserToSpace_emptyGUID_returnIllegalArgumentException() {
        sut.assignUserToOrganization(null, null);
    }

    @SuppressWarnings(value = "unchecked")
    private void mockGetForEntity(Class clazz, boolean pathVars) {
        ResponseEntity responseEntityMock = mock(ResponseEntity.class);
        Object mockedBodyObject;
        if (clazz.equals(String.class)) {
            mockedBodyObject = "";
        }
        else {
            mockedBodyObject = mock(clazz);
        }

        when(responseEntityMock.getBody()).thenReturn(mockedBodyObject);

        if (pathVars) {
            when(template.getForEntity(anyString(), any(), anyMapOf(String.class, Object.class)))
                .thenReturn(responseEntityMock);
        }
        else {
            when(template.getForEntity(anyString(), any())).thenReturn(responseEntityMock);
        }
    }

    private void mockPostForEntity() {
        when(template.postForEntity(anyString(), any(), any()))
            .thenReturn(mock(ResponseEntity.class));
    }

    @Test public void getOrg_correctData_correctRestParams() {
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/organizations/{org}";
        mockGetForEntity(CcOrg.class, true);

        sut.getOrg(guid).toBlocking().single();

        verify(template).getForEntity(eq(apiUrl), eq(CcOrg.class), anyMapOf(String.class,
                Object.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getOrg_emptyGUID_returnIllegalArgumentException() {
        sut.getOrg(null);
    }

    /*@Test public void getOrgs_correctData_structureReturned() {
        String apiUrl = BASE_URL + "/v2/organizations";
        verifyGetForEntity(sut::getOrgs, apiUrl, CcOrgsList.class);
    }*/

    @SuppressWarnings(value = "unchecked")
    private void verifyGetForEntity(Supplier<Collection> supplier, String apiUrl, Class verifyClass){
        mockGetForEntity(verifyClass, false);
        supplier.get();
        verify(template).getForEntity(eq(apiUrl), eq(verifyClass));
    }

    @Test public void getSpace_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/spaces/{space}";
        verifyGetForEntityForObservable(sut::getSpace, apiUrl, CcSpace.class);
    }

    @SuppressWarnings(value = "unchecked")
    private void verifyGetForEntity(Function<UUID, Object> fun, String apiUrl, Class verifyType){
        UUID guid = UUID.randomUUID();
        mockGetForEntity(verifyType, true);
        fun.apply(guid);

        verify(template).getForEntity(eq(apiUrl), eq(verifyType), anyMapOf(String.class,
                Object.class));
    }

    @SuppressWarnings(value = "unchecked")
    private <T> void verifyGetForEntityForObservable(Function<UUID, Observable<T>> fun, String apiUrl, Class verifyType){
        UUID guid = UUID.randomUUID();
        mockGetForEntity(verifyType, true);
        fun.apply(guid).toBlocking().single();

        verify(template).getForEntity(eq(apiUrl), eq(verifyType), anyMapOf(String.class,
            Object.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSpace_emptyGUID_returnIllegalArgumentException() {
        sut.getSpace(null);
    }

    @Test public void getSpaceSummary_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/spaces/{space}/summary?inline-relations-depth=1";
        verifyGetForEntity(sut::getSpaceSummary, apiUrl, CcSummary.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSpaceSummary_emptyGUID_returnIllegalArgumentException() {
        sut.getSpaceSummary(null);
    }

    @Test public void createServiceInstance_correctData_correctRestParams() {
        CcNewServiceInstance serviceInstance = mock(CcNewServiceInstance.class);
        when(serviceInstance.getName()).thenReturn("");
        when(serviceInstance.getPlanGuid()).thenReturn(UUID.randomUUID());
        when(serviceInstance.getSpaceGuid()).thenReturn(UUID.randomUUID());
        when(serviceInstance.getOrganizationGuid()).thenReturn(UUID.randomUUID());
        String apiUrl = BASE_URL + "/v2/service_instances";

        mockPostForEntity();

        sut.createServiceInstance(serviceInstance).toBlocking().single();

        verify(template).postForEntity(apiUrl, serviceInstance, CcExtendedServiceInstance.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createServiceInstance_emptyGUID_returnIllegalArgumentException() {
        sut.createServiceInstance(null);
    }

    @Test public void deleteServiceInstance_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/service_instances/{instance}";
        verifyWithDelete(sut::deleteServiceInstance, apiUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteServiceInstance_emptyGUID_returnIllegalArgumentException() {
        sut.deleteServiceInstance(null);
    }

    @Test public void getAppSummary_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/apps/{app}/summary";
        verifyGetForEntity(sut::getAppSummary, apiUrl, CcAppSummary.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAppSummary_emptyGUID_returnIllegalArgumentException() {
        sut.getAppSummary(null);
    }

    @Test public void restageApp_correctData_correctRestParams() {
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/apps/{app}/restage";

        sut.restageApp(guid);

        verify(template).postForEntity(eq(apiUrl), eq(null), eq(String.class), anyMapOf(String.class, Object.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void restageApp_emptyGUID_returnIllegalArgumentException() {
        sut.restageApp(null);
    }

    @Test(expected = NullPointerException.class)
    public void getAppBindings_emptyGUID_returnIllegalArgumentException() {
        sut.getAppBindings(null);
    }

    @Test public void deleteApp_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/apps/{app}?recursive=true";
        verifyWithDelete(sut::deleteApp, apiUrl);
    }

    @Test public void switchApp_correctData_correctRestParams_shouldSwitchOn() {
        String apiUrl = BASE_URL + "/v2/apps/{app}";
        UUID guid = UUID.randomUUID();
        Map<String, Object> serviceRequest = new HashMap<String, Object>();
        CcAppState state = CcAppState.STARTED;
        CcAppStatus status = new CcAppStatus(state);
        serviceRequest.put("state", status.getState());
        sut.switchApp(guid, status);
        verify(template).put(eq(apiUrl), eq(serviceRequest), anyMapOf(String.class, String.class));
    }

    @Test public void switchApp_correctData_correctRestParams_shouldSwitchOff() {
        String apiUrl = BASE_URL + "/v2/apps/{app}";
        UUID guid = UUID.randomUUID();
        Map<String, Object> serviceRequest = new HashMap<String, Object>();
        CcAppState state = CcAppState.STOPPED;
        CcAppStatus status = new CcAppStatus(state);
        serviceRequest.put("state", status.getState());
        sut.switchApp(guid, status);
        verify(template).put(eq(apiUrl), eq(serviceRequest), anyMapOf(String.class, String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteApp_emptyGUID_returnIllegalArgumentException() {
        sut.deleteApp(null);
    }

    @Test public void createServiceBinding_correctData_correctRestParams() {
        CcNewServiceBinding serviceBinding = mock(CcNewServiceBinding.class);

        when(serviceBinding.getAppGuid()).thenReturn(UUID.randomUUID());
        when(serviceBinding.getServiceInstanceGuid()).thenReturn(UUID.randomUUID());
        String apiUrl = BASE_URL + "/v2/service_bindings";

        mockPostForEntity();

        sut.createServiceBinding(serviceBinding);

        verify(template).postForEntity(apiUrl, serviceBinding, CcServiceBinding.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createServiceBinding_emptyGUID_returnIllegalArgumentException() {
        sut.createServiceBinding(null);
    }

    @Test public void deleteServiceBinding_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/service_bindings/{binding}";
        verifyWithDelete(sut::deleteServiceBinding, apiUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteServiceBinding_emptyGUID_returnIllegalArgumentException() {
        sut.deleteServiceInstance(null);
    }

    @Test public void getUserOrgs_correctData_correctRestParams() {
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/users/{userId}/organizations";
        when(template.getForObject(anyString(), any(), any(UUID.class)))
            .thenReturn(mock(CcOrgsList.class));
        sut.getUserOrgs(guid);

        verify(template).getForObject(eq(apiUrl), any(), any(UUID.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserOrgs_emptyGUID_returnIllegalArgumentException() {
        sut.getUserOrgs(null);
    }

    @Test public void getOrgUsers_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/organizations/{guid}/developers";
        check_get_for_object(sut::getOrgUsers, apiUrl);
    }

    @Test public void getSpaceUsers_correctData_correctRestParams() {
        String apiUrl = BASE_URL + "/v2/spaces/{guid}/developers";
        check_get_for_object(sut::getSpaceUsers, apiUrl);
    }

    private void check_get_for_object(BiFunction<UUID, Role, Collection<User>> fun, String apiUrl){
        UUID guid = UUID.randomUUID();

        when(template.getForObject(anyString(), any(), any(UUID.class)))
            .thenReturn(mock(CcOrgUsersList.class));
        fun.apply(guid, Role.DEVELOPERS);
        verify(template).getForObject(eq(apiUrl), any(), any(UUID.class));

    }

    @Test public void deleteUser_correctData_correctRestParams() {
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/users/{guid}";

        sut.deleteUser(guid);

        verify(template).delete(apiUrl, guid);
    }

    @Test public void revokeOrgRole_correctData_correctRestParams() {

        applyAssignRole(sut::revokeOrgRole, "organizations", this::verifyDeleteWithArgumentCapture);
    }

    @Test public void revokeSpaceRole_correctData_correctRestParams() {
        applyAssignRole(sut::revokeSpaceRole, "spaces", this::verifyDeleteWithArgumentCapture);
    }

    @Test public void assignOrgRole_correctData_correctRestParams() {
        applyAssignRole(sut::assignOrgRole, "organizations", this::verifyPutWithArgumentCapture);
    }

    @Test public void assignSpaceRole_correctData_correctRestParams() {
        applyAssignRole(sut::assignSpaceRole, "spaces", this::verifyPutWithArgumentCapture);
    }

    private ArgumentCaptor<Map> verifyPutWithArgumentCapture(String url){
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(template).put(eq(url), eq(null), argument.capture());
        return argument;
    }

    private ArgumentCaptor<Map> verifyDeleteWithArgumentCapture(String url){
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(template).delete(eq(url), argument.capture());
        return argument;
    }

    private void applyAssignRole(TriConsumer<UUID, UUID, Role> consumer, String assertedValue,
            Function<String, ArgumentCaptor<Map>> verifier){
        UUID guid = UUID.randomUUID();
        String apiUrl = BASE_URL + "/v2/{type}/{orgSpace}/{role}/{user}";
        consumer.accept(guid, guid, Role.USERS);

        ArgumentCaptor<Map> argument = verifier.apply(apiUrl);

        assertTrue(argument.getValue().containsValue(assertedValue));
    }

    private void verifyWithDelete(Consumer<UUID> consumer, String apiUrl){
        UUID guid = UUID.randomUUID();
        consumer.accept(guid);
        verify(template).delete(eq(apiUrl), anyMapOf(String.class, Object.class));
    }

    private void verifyAddingUser(BiConsumer<UUID, UUID> consumer, String apiUrl1, String apiUrl2){
        UUID guid = UUID.randomUUID();

        consumer.accept(guid, guid);

        verify(template).put(eq(apiUrl1), eq(null), anyMapOf(String.class, Object.class));
        verify(template).put(eq(apiUrl2), eq(null), anyMapOf(String.class, Object.class));
    }
}


@FunctionalInterface
interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);
}

