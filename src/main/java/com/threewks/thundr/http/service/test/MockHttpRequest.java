package com.threewks.thundr.http.service.test;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.http.service.HttpRequest;
import com.threewks.thundr.http.service.HttpResponse;

public class MockHttpRequest implements HttpRequest {

	private HttpResponse expect;
	private String url;
	private long timeout;
	private boolean followRedirects;
	private Map<String, String> headers = new LinkedHashMap<String, String>();
	private Object body;
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private String contentType;
	private List<HttpCookie> cookies = new ArrayList<HttpCookie>();
	private String method;

	public MockHttpRequest(String url, HttpResponse expect) {
		this.expect = expect;
	}

	@Override
	public HttpRequest followRedirects(boolean value) {
		this.followRedirects = value;
		return this;
	}

	@Override
	public HttpRequest timeout(long millis) {
		this.timeout = millis;
		return this;
	}

	@Override
	public HttpRequest body(Object body) {
		this.body = body;
		return this;
	}

	@Override
	public HttpRequest header(String name, String value) {
		headers.put(name, value);
		return this;
	}

	@Override
	public HttpRequest headers(Map<String, String> headers) {
		this.headers.putAll(headers);
		return this;
	}

	@Override
	public HttpRequest parameter(String name, String value) {
		this.parameters.put(name, value);
		return this;
	}

	@Override
	public HttpRequest parameter(String name, Object value) {
		this.parameters.put(name, value);
		return this;
	}

	@Override
	public HttpRequest parameters(Map<String, Object> parameters) {
		this.parameters.putAll(parameters);
		return this;
	}

	@Override
	public HttpRequest contentType(ContentType contentType) {
		this.contentType = contentType.value();
		return this;
	}
	
	@Override
	public HttpRequest contentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	@Override
	public HttpRequest cookie(HttpCookie cookie) {
		this.cookies.add(cookie);
		return this;
	}

	@Override
	public HttpRequest cookies(Collection<HttpCookie> cookie) {
		this.cookies.addAll(cookie);
		return this;
	}

	@Override
	public HttpResponse get() {
		method = "GET";
		return expect;
	}

	@Override
	public HttpResponse post() {
		method = "POSt";
		return expect;
	}

	@Override
	public HttpResponse put() {
		method = "PUT";
		return expect;
	}

	@Override
	public HttpResponse delete() {
		method = "DELETE";
		return expect;
	}

	@Override
	public HttpResponse head() {
		method = "HEAD";
		return expect;
	}

	public HttpResponse getExpect() {
		return expect;
	}

	public String getUrl() {
		return url;
	}

	public long getTimeout() {
		return timeout;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Object getBody() {
		return body;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getContentType() {
		return contentType;
	}

	public List<HttpCookie> getCookies() {
		return cookies;
	}

	public String getMethod() {
		return method;
	}
}
