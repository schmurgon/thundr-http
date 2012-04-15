package com.atomicleopard.webFramework.http.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.atomicleopard.webFramework.http.Cookie;
import com.atomicleopard.webFramework.http.FileParameter;

public interface HttpRequest {
	public HttpRequest authenticate(String username, String password);

	/**
	 * Indicate if the WS should continue when hitting a 301 or 302
	 * 
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest followRedirects(boolean value);

	public HttpRequest timeout(long millis);

	public HttpRequest files(FileParameter... fileParameters);

	public HttpRequest files(List<FileParameter> fileParameters);

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

	public HttpRequest cookie(Cookie cookie);

	public HttpRequest cookies(Collection<Cookie> cookie);

	/**
	 * Adds the given parameters to request.
	 */
	public HttpRequest parameters(Map<String, Object> parameters);

	public HttpResponse get();

	public HttpResponse post();

	public HttpResponse put();

	public HttpResponse delete();

	public HttpResponse options();

	public HttpResponse head();
}
