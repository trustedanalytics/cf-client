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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class FilterQueryTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        // @formatter:off
        return Arrays.asList(new Object[][] {
            {Filter.APP_GUID, FilterOperator.EQ, new Object[] {"1"}, "app_guid:1"},
            {Filter.APP_GUID, FilterOperator.GT, new Object[] {"2"}, "app_guid>2"},
            {Filter.APP_GUID, FilterOperator.LT, new Object[] {"3"}, "app_guid<3"},
            {Filter.APP_GUID, FilterOperator.IN, new Object[] {"4"}, "app_guid IN 4"},
            {Filter.APP_GUID, FilterOperator.IN, new Object[] {"5", "6", "7"}, "app_guid IN 5,6,7"}
        });
        // @formatter:on
    }

    @Parameterized.Parameter(0)
    public Filter filter;

    @Parameterized.Parameter(1)
    public FilterOperator operator;

    @Parameterized.Parameter(2)
    public Object[] params;

    @Parameterized.Parameter(3)
    public String expected;

    @Test
    public void testQueryFormat() {
        Assert.assertEquals(expected, FilterQuery.from(filter, operator, params).format());
    }

}
