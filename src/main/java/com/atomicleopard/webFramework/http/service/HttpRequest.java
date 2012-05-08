package com.atomicleopard.webFramework.http.service;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;

import com.atomicleopard.webFramework.http.ContentType;

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

	// Authentication not implemented
	// public HttpRequest authenticate(String username, String password);
	// public HttpRequest authenticate(String username, String password, String scheme) {

	// Attachments and multipart not implemented
	// public HttpRequest files(FileParameter... fileParameters);
	// public HttpRequest files(List<FileParameter> fileParameters);
}
