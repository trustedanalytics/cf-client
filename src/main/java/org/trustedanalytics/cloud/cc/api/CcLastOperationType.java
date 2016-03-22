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


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Arrays;

@JsonSerialize(using = ToStringSerializer.class)
public enum CcLastOperationType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String type;

    CcLastOperationType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toLowerCase();
    }

    @JsonCreator
    public static CcLastOperationType Create(String value) {
        return Arrays.asList(CcLastOperationType.values())
                .stream()
                .filter(enm -> enm.type.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unable to deserialize %s from %s",
                        CcLastOperationType.class, value)));
    }
}