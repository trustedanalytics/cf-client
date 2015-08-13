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
package org.trustedanalytics.cloud.cc.api.queries;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@FunctionalInterface
public interface FilterQuery {

    String format();

    static FilterQuery from(Filter filter, FilterOperator operator, Object... params) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(operator);
        Objects.requireNonNull(params);

        return () -> {
            final StringBuilder builder = new StringBuilder(filter.toString());

            if (operator == FilterOperator.IN) {
                builder.append(" ").append(operator.toString()).append(" ");
            } else {
                builder.append(operator.toString());
            }

            return builder.append(Arrays.asList(params).stream().map(Object::toString)
                .collect(Collectors.joining(","))).toString();
        };
    }
}
