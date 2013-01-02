/*
 * This file is a component of thundr, a software library from 3wks.
 * Read more: http://www.3wks.com.au/thundr
 * Copyright (C) 2013 3wks, <thundr@3wks.com.au>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.threewks.thundr.http.service.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.http.service.HttpResponse;
import com.threewks.thundr.http.service.HttpService;

public class MockHttpService implements HttpService {
	private HttpResponse expect = new MockHttpResponse();

	public MockHttpService expected(HttpResponse expect) {
		this.expect = expect;
		return this;
	}

	@Override
	public MockHttpRequest request(String url) {
		return new MockHttpRequest(url, expect);
	}

	@Override
	public <T> InputStream convertOutgoing(T t) {
		return new ByteArrayInputStream(new byte[0]);
	}

	@Override
	public <T> T convertIncoming(InputStream is, Class<T> type) {
		return null;
	}

	@Override
	public <T> void addOutgoingTypeConvertor(Class<T> type, ETransformer<T, InputStream> transformer) {
	}

	@Override
	public <T> void addIncomingTypeConvertor(Class<T> type, ETransformer<InputStream, T> transformer) {
	}

}
