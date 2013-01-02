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
package com.threewks.thundr.http.service;

import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface HttpResponse {
	public int getStatus();

	public String getContentType();

	public String getHeader(String name);

	public List<String> getHeaders(String name);

	public Map<String, List<String>> getHeaders();

	public HttpCookie getCookie(String cookieName);

	public List<HttpCookie> getCookies(String name);

	public List<HttpCookie> getCookies();

	public String getBody();

	public <T> T getBody(Class<T> as);

	public byte[] getBodyAsBytes();

	public InputStream getBodyAsStream();

	public URI getUri();
}
