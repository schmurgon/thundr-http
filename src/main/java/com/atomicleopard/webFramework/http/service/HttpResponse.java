package com.atomicleopard.webFramework.http.service;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

import com.atomicleopard.webFramework.http.Cookie;

public interface HttpResponse {
	public int getStatus();

	public String getStatusText();

	public String getContentType();

	public String getHeader(String name);

	public Map<String, List<String>> getHeaders();

	public List<Cookie> getCookies();

	public String getBody();

	public <T> T getBody(Class<T> as);

	public byte[] getBodyAsBytes();

	public InputStream getBodyAsStream();

	public URI getUri();

	public Cookie getCookie(String cookieName);
}
