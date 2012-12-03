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
