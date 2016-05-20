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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;

@RunWith(Parameterized.class)
public class FeignErrorDecoderHandlerTest {

    @Parameterized.Parameters(name = "{index} - {0}")
    public static Iterable<Object[]> data() {
        // @formatter:off
        return Arrays.asList(new Object[][] {
            {"Decode expected string field",
                // given
                new FeignErrorDecoderHandler("description"),
                Response.create(404, "", Collections.emptyMap(), mockBody("{\"description\": \"value\"}")),
                // then
                allOf(
                    isA(FeignResponseException.class),
                    hasProperty("message", is("value"))
                )
            },
            {"Decode expected integer field",
                // given
                new FeignErrorDecoderHandler("description"),
                Response.create(404, "", Collections.emptyMap(), mockBody("{\"description\": 1}")),
                // then
                allOf(
                    isA(FeignResponseException.class),
                    hasProperty("message", is("1"))
                )
            },
            {"Decode expected object field",
                // given
                new FeignErrorDecoderHandler("description"),
                Response.create(404, "", Collections.emptyMap(), mockBody("{\"description\": {\"key\":\"value\"}}")),
                // then
                isA(FeignException.class)
            },
            {"Decode not expected field",
                // given
                new FeignErrorDecoderHandler("description"),
                Response.create(404, "", Collections.emptyMap(), mockBody("{\"not_expected\": \"value\"}")),
                // then
                isA(FeignException.class)
            },
            {"Decode multiple expected fields",
                // given
                new FeignErrorDecoderHandler("description", "code"),
                Response.create(404, "", Collections.emptyMap(), mockBody("{\"description\": \"value\", \"code\": 1}")),
                // then
                allOf(
                    isA(FeignResponseException.class),
                    hasProperty("message", is("value, 1"))
                )
            }
        });
    }

    @Parameterized.Parameter(0)
    public String testname;

    @Parameterized.Parameter(1)
    public ErrorDecoderHandler handler;

    @Parameterized.Parameter(2)
    public Response response;

    @Parameterized.Parameter(3)
    public Matcher<Exception> matcher;

    @Test
    public void testFeignErrorDecoderHandlerTest() {
        // given
        final ErrorDecoder decoder = new CompositeErrorDecoder(handler);

        // when
        final Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception, matcher);
    }

    private static Response.Body mockBody(String content) {
        try {
            final Response.Body body = mock(Response.Body.class);

            when(body.asInputStream()).thenAnswer(invocation -> IOUtils.toInputStream(content));
            when(body.asReader()).thenReturn(new StringReader(content));

            return body;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mock Body");
        }
    }
}
