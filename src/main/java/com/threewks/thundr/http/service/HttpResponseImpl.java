package com.threewks.thundr.http.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.atomicleopard.expressive.Expressive;
import com.threewks.thundr.http.HttpSupport;
import com.threewks.thundr.profiler.Profiler;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;

public class HttpResponseImpl implements HttpResponse {
	private Future<HTTPResponse> future;
	private HTTPResponse response;
	private Map<String, List<String>> headers;
	private HttpService service;
	private Map<String, List<HttpCookie>> cookies;
	private Profiler profiler;

	public HttpResponseImpl(Future<HTTPResponse> future, HttpService service) {
		this.future = future;
		this.service = service;
		this.profiler = profiler == null ? Profiler.None : profiler;
	}

	@Override
	public int getStatus() {
		return response().getResponseCode();
	}

	@Override
	public String getContentType() {
		return getHeader(HttpSupport.HttpHeaderContentType);
	}

	@Override
	public String getHeader(String name) {
		List<String> headers = getHeaders().get(name);
		return headers == null ? null : headers.get(0);
	}

	@Override
	public List<String> getHeaders(String name) {
		return getHeaders().get(name);
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		response();
		return Collections.unmodifiableMap(headers);
	}

	@Override
	public HttpCookie getCookie(String cookieName) {
		List<HttpCookie> cookies = getCookies(cookieName);
		return cookies == null ? null : cookies.get(0);
	}

	@Override
	public List<HttpCookie> getCookies(String name) {
		response();
		return cookies.get(name);
	}

	@Override
	public List<HttpCookie> getCookies() {
		response();
		return Expressive.flatten(cookies.values());
	}

	@Override
	public String getBody() {
		return getBody(String.class);
	}

	@Override
	public <T> T getBody(Class<T> as) {
		return service.convertIncoming(getBodyAsStream(), as);
	}

	@Override
	public byte[] getBodyAsBytes() {
		return response().getContent();
	}

	@Override
	public InputStream getBodyAsStream() {
		return new ByteArrayInputStream(response().getContent());
	}

	@Override
	public URI getUri() {
		try {
			return response().getFinalUrl().toURI();
		} catch (URISyntaxException e) {
			throw new HttpResponseException(e, "Uri cannot be parsed: %s", e.getMessage());
		}
	}

	private HTTPResponse response() {
		if (response == null) {
			try {
				response = future.get();
				headers = buildHeaderMap();
				cookies = buildCookieMap();
				return response;
			} catch (InterruptedException e) {
				throw new HttpRequestException("Failed to wait for completion of asynchronous request: %s", e.getMessage());
			} catch (ExecutionException e) {
				throw new HttpRequestException(e, "Failed to get result for asynchronous request: %s", e.getMessage());
			}
		}
		return response;
	}

	private Map<String, List<String>> buildHeaderMap() {
		Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
		for (HTTPHeader header : response.getHeaders()) {
			String key = header.getName();
			String value = header.getValue();
			List<String> values = headers.get(key);
			if (values == null) {
				values = new ArrayList<String>();
				headers.put(key, values);
			}
			values.add(value);
		}
		return headers;
	}

	private Map<String, List<HttpCookie>> buildCookieMap() {
		Map<String, List<HttpCookie>> cookies = new LinkedHashMap<String, List<HttpCookie>>();
		List<String> cookieHeaders = headers.get(HttpSupport.HttpHeaderSetCookie);
		if (cookieHeaders != null) {
			for (String setCookieHeader : cookieHeaders) {
				List<HttpCookie> cookieSet = HttpCookie.parse(setCookieHeader);
				for (HttpCookie httpCookie : cookieSet) {
					String name = httpCookie.getName();
					List<HttpCookie> existingCookies = cookies.get(name);
					if (existingCookies == null) {
						existingCookies = new ArrayList<HttpCookie>();
						cookies.put(name, existingCookies);
					}
					existingCookies.add(httpCookie);
				}
			}
		}
		return cookies;
	}
}
