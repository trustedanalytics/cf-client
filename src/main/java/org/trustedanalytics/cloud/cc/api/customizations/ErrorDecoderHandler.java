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

import feign.Response;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Error decoder handler is responsible for testing whether it can be applied to response with
 * particular http status code and transforming it to exception object.
 */
public interface ErrorDecoderHandler extends BiFunction<String, Response, Exception>, Predicate<Response> {
}
