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

import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;

import com.threewks.thundr.http.ContentType;

public interface HttpRequest {
	/**
	 * Indicate if the WS should continue when hitting a 301 or 302
	 * 
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest followRedirects(boolean value);

	public HttpRequest timeout(long millis);

	public HttpRequest body(Object body);

	public HttpRequest header(String name, String value);

	public HttpRequest headers(Map<String, String> headers);

	/**
	 * Adds the given parameters to request.
	 * GET: parameters are passed as a query string
	 * POST: parameters are passed in the body
	 * PUT: parameters are passed in the body
	 * DELETE: parameters are passed as a query string
	 */
	public HttpRequest parameter(String name, String value);

	public HttpRequest parameter(String name, Object value);

	/**
	 * Adds the given parameters to request.
	 */
	public HttpRequest parameters(Map<String, Object> parameters);

	public HttpRequest contentType(ContentType contentType);

	public HttpRequest contentType(String contentType);

	public HttpRequest cookie(HttpCookie cookie);

	public HttpRequest cookies(Collection<HttpCookie> cookie);

	public HttpResponse get();

	public HttpResponse post();

	public HttpResponse put();

	public HttpResponse delete();

	public HttpResponse head();

	/**
	 * Causes the request to be authorized using the Basic scheme and the provided credentials.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return the {@link HttpRequest} for method chaining
	 * 
	 * @throws HttpRequestException if the Basic scheme is not supported
	 */
	public HttpRequest authorize(String username, String password);

	/**
	 * Causes the request to be authorized using the given scheme and the provided credentials.
	 * 
	 * @param username
	 * @param password
	 * @param scheme
	 * 
	 * @return the {@link HttpRequest} for method chaining
	 * 
	 * @throws HttpRequestException if the given scheme is not supported
	 */
	public HttpRequest authorize(String username, String password, String scheme);

	// Attachments and multipart not implemented
	// public HttpRequest files(FileParameter... fileParameters);
	// public HttpRequest files(List<FileParameter> fileParameters);
}
