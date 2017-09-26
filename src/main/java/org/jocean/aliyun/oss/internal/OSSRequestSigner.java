package org.jocean.aliyun.oss.internal;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.aliyun.oss.ClientException;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.ServiceSignature;

import io.netty.handler.codec.http.HttpRequest;

public class OSSRequestSigner {

    /* Note that resource path should not have been url-encoded. */
    private String resourcePath;
    private Credentials creds;

    public OSSRequestSigner(final String resourcePath, final Credentials creds) {
        this.resourcePath = resourcePath;
        this.creds = creds;
    }

    public void sign(final HttpRequest request) throws ClientException {
        String accessKeyId = creds.getAccessKeyId();
        String secretAccessKey = creds.getSecretAccessKey();

        if (accessKeyId.length() > 0 && secretAccessKey.length() > 0) {
            final String canonicalString = SignUtils.buildCanonicalString(
                    request.method().toString(), resourcePath, request, null);
            final String signature = ServiceSignature.create().computeSignature(secretAccessKey, canonicalString);
            request.headers().add(OSSHeaders.AUTHORIZATION, 
                OSSUtils.composeRequestAuthorization(accessKeyId, signature));
        } 
    }
}
