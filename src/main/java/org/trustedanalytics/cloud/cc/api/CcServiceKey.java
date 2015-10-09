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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CcServiceKey {

    private CcMetadata metadata;

    private CcServiceKeyEntity entity;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(metadata.getGuid()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CcServiceKey)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        CcServiceKey service = (CcServiceKey)obj;
        return new EqualsBuilder()
            .append(metadata.getGuid(), service.getMetadata().getGuid())
            .isEquals();
    }
}
