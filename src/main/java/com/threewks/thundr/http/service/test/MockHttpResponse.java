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

import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;

import com.threewks.thundr.http.service.HttpResponse;

public class MockHttpResponse implements HttpResponse {

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public String getHeader(String name) {
		return null;
	}

	@Override
	public List<String> getHeaders(String name) {
		return null;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return null;
	}

	@Override
	public HttpCookie getCookie(String cookieName) {
		return null;
	}

	@Override
	public List<HttpCookie> getCookies(String name) {
		return null;
	}

	@Override
	public List<HttpCookie> getCookies() {
		return null;
	}

	@Override
	public String getBody() {
		return null;
	}

	@Override
	public <T> T getBody(Class<T> as) {
		return null;
	}

	@Override
	public byte[] getBodyAsBytes() {
		return null;
	}

	@Override
	public InputStream getBodyAsStream() {
		return null;
	}

	@Override
	public URI getUri() {
		return null;
	}
}
