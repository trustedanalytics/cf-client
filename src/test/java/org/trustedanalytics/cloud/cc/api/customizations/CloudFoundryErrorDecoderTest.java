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

import com.google.common.collect.ImmutableList;

import feign.FeignException;
import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@RunWith(Parameterized.class)
public class CloudFoundryErrorDecoderTest {

    private static final String INVALID_JSON = "";

    private static final String GENERIC_JSON_CONTENT =
        "{\"key\": \"value\"}";

    private static final String CF_JSON_CONTENT =
        "{\"code\": 100004, \"description\": \"description\", \"error_code\": \"error_code\"}";

    @Parameterized.Parameters(name = "{index} - {0}")
    public static Iterable<Object[]> data() {
        // @formatter:off
        return Arrays.asList(new Object[][] {
            {"Test decode cloud foundry error code",
                // given
                new ArrayList<ErrorDecoderHandler>(),
                Response.create(404, "", Collections.emptyMap(), mockBody(CF_JSON_CONTENT)),
                // then
                allOf(
                    isA(CloudFoundryException.class),
                    hasProperty("httpCode", is(404)),
                    hasProperty("cfCode", is(100004)),
                    hasProperty("message", is("description")),
                    hasProperty("errorCode", is("error_code"))
                )
            },
            {"Test decode invalid json",
                // given
                new ArrayList<ErrorDecoderHandler>(),
                Response.create(404, "", Collections.emptyMap(), mockBody(INVALID_JSON)),
                // then
                isA(FeignException.class)
            },
            {"Test decode generic error code",
                // given
                new ArrayList<ErrorDecoderHandler>(),
                Response.create(404, "", Collections.emptyMap(), mockBody(GENERIC_JSON_CONTENT)),
                // then
                isA(FeignException.class)
            },
            {"Test decode using always matching custom error handler",
                // given
                ImmutableList.of(new TestErrorDecoderHandler(response -> true)),
                Response.create(404, "", Collections.emptyMap(), mockBody(GENERIC_JSON_CONTENT)),
                // then
                allOf(
                    isA(RuntimeException.class),
                    hasProperty("message", is("test"))
                )
            },
            {"Test decode using specific matching custom error handler",
                // given
                ImmutableList.of(new TestErrorDecoderHandler(response -> response.status() == 400)),
                Response.create(400, "", Collections.emptyMap(), mockBody(GENERIC_JSON_CONTENT)),
                // then
                allOf(
                    isA(RuntimeException.class),
                    hasProperty("message", is("test"))
                )
            },
            {"Test decode using not matching custom error handler",
                // given
                ImmutableList.of(new TestErrorDecoderHandler(response -> response.status() == 400)),
                Response.create(404, "", Collections.emptyMap(), mockBody(CF_JSON_CONTENT)),
                // then
                allOf(
                    isA(CloudFoundryException.class),
                    hasProperty("httpCode", is(404)),
                    hasProperty("cfCode", is(100004)),
                    hasProperty("message", is("description")),
                    hasProperty("errorCode", is("error_code"))
                )
            }
        });
        // @formatter:on
    }

    @Parameterized.Parameter(0)
    public String testname;

    @Parameterized.Parameter(1)
    public Collection<ErrorDecoderHandler> handlers;

    @Parameterized.Parameter(2)
    public Response response;

    @Parameterized.Parameter(3)
    public Matcher<Exception> matcher;

    @Test
    public void testCloudFoundryErrorDecoder() {
        // given
        final ErrorDecoder decoder = new CloudFoundryErrorDecoder(handlers);

        // when
        final Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception, matcher);
    }

    private static Body mockBody(String content) {
        try {
            final Body body = mock(Body.class);

            when(body.asInputStream()).thenReturn(IOUtils.toInputStream(content));
            when(body.asReader()).thenReturn(new StringReader(content));

            return body;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mock Body");
        }
    }

    private static class TestErrorDecoderHandler implements ErrorDecoderHandler {
        private final Predicate<Response> predicate;

        TestErrorDecoderHandler(Predicate<Response> predicate) {
            this.predicate = predicate;
        }

        @Override
        public Exception apply(String s, Response response) {
            return new RuntimeException("test");
        }

        @Override
        public boolean test(Response response) {
            return predicate.test(response);
        }
    }
}
