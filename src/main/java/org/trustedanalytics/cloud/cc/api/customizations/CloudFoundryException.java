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
package org.trustedanalytics.cloud.cc.api.customizations;

import lombok.ToString;

@ToString
public class CloudFoundryException extends RuntimeException {

    private final int httpCode;
    private final int cfCode;
    private final String description;
    private final String errorCode;

    public CloudFoundryException(int httpCode, int cfCode, String description, String errorCode) {
        super(description);
        this.httpCode = httpCode;
        this.cfCode = cfCode;
        this.description = description;
        this.errorCode = errorCode;
    }

    public int getCfCode() {
        return cfCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getDescription() {
        return description;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
