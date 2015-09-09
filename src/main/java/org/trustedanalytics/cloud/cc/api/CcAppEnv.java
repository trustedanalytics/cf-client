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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;

public class CcAppEnv {

    @JsonProperty("env")
    private final Object env;

    public CcAppEnv (Object env) {
        this.env = env;
    }

    /**
     * <pre>
     * Method to get value by specified filter
     * Example json:
     * {
     * "user-provided": [
     *   {
     *        "name": "sso",
     *        "label": "user-provided",
     *        "tags": [],
     *        "credentials": {
     *          "apiEndpoint": "http://api.example.eu"
     *        }
     *   }]}
     *
     * To get api endpoint from such json we could use this method like:
     * String apiEndpoint = ccAppEnv.getValueByFilter("$..[?(@.name=='sso')]..credentials..apiEndpoint").toString();
     * </pre>
     * @param filter is an expression like: "$..[?(@.name=='sso')]..credentials..apiEndpoint"
     */
    public JsonNode getValueByFilter(String filter) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(JsonPath.parse(env).read(filter).toString());
    }

    /**
     * <pre>
     * Method to get property from node specified by key and value
     *
     * Example json:
     * {
     * "user-provided": [
     *   {
     *       "name": "sso",
     *       "label": "user-provided",
     *       "tags": [],
     *       "credentials": {
     *           "apiEndpoint": "http://api.example.eu"
     *       }
     *   }]}
     * To get credentials from such json we could use this method like:
     * JsonNode credentials = ccAppEnv.getPropertyFromNodeSpecifiedByKeyAndValue("name", "sso", "credentials");
     *
     * To get api endpoint from such json we could use this method like:
     * String apiEndpoint = ccAppEnv.getPropertyFromNodeSpecifiedByKeyAndValue("name", "sso", "credentials").findValue("apiEndpoint").toString();
     * </pre>
     * @param property is a property you like to get back
     */
    public JsonNode getPropertyFromNodeSpecifiedByKeyAndValue(String key, String value, String property) throws IOException {
        String filter = "$..[?(@." + key + "=='" + value + "')]." + property;
        return getValueByFilter(filter);
    }

    /**
     * <pre>
     * Method to find service credentials property by specified service label
     * Example json:
     * {
     * "user-provided": [
     *   {
     *        "name": "sso",
     *        "label": "user-provided",
     *        "tags": [],
     *        "credentials": {
     *          "apiEndpoint": "http://api.example.eu"
     *        }
     *   }]}
     *
     * To get api endpoint from such json we could use this method like:
     * String apiEndpoint = ccAppEnv.findCredentialsPropertyByServiceLabel("user-provided", "apiEndpoint");
     * </pre>
     * @param label is a service label
     * @param credentialsProperty is a service credentials property
     */
    public String findCredentialsPropertyByServiceLabel(String label, String credentialsProperty) throws IOException {
        String filter = "$..[?(@.label=='" + label + "')]..credentials.." + credentialsProperty;
        return toValidProperty(getValueByFilter(filter));
    }

    /**
     * <pre>
     * Method to find service credentials property by specified service name
     * Example json:
     *
     * {
     * "user-provided": [
     *   {
     *        "name": "sso",
     *        "label": "user-provided",
     *        "tags": [],
     *        "credentials": {
     *          "apiEndpoint": "http://api.example.eu"
     *        }
     *   }]}
     *
     *
     * To get api endpoint from such json we could use this method like:
     * String apiEndpoint = ccAppEnv.findCredentialsPropertyByServiceName("sso", "apiEndpoint");
     * </pre>
     * @param name is a service name
     * @param credentialsProperty is a service credentials property
     */
    public String findCredentialsPropertyByServiceName(String name, String credentialsProperty) throws IOException {
        String filter = "$..[?(@.name=='" + name + "')]..credentials.." + credentialsProperty;
        return toValidProperty(getValueByFilter(filter));
    }

    private String toValidProperty(JsonNode property) {
        String validatedProperty = property.get(0).toString();
        if (validatedProperty.startsWith("\"") && validatedProperty.endsWith("\"")) {
            return validatedProperty.substring(1, validatedProperty.length() - 1);
        }
        return validatedProperty;
    }
}
