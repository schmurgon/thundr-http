package com.atomicleopard.webFramework.http.service;

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
