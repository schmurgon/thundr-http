package com.atomicleopard.webFramework.http.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.atomicleopard.expressive.transform.ETransformers;
import com.atomicleopard.webFramework.http.Cookie;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

public class HttpResponseImpl implements HttpResponse {

	private ListenableFuture<Response> future;
	private Response response;
	private HttpService service;

	public HttpResponseImpl(ListenableFuture<Response> listenableFuture, HttpService service) {
		this.future = listenableFuture;
		this.service = service;
	}

	@Override
	public int getStatus() {
		return response().getStatusCode();
	}

	@Override
	public String getStatusText() {
		return response().getStatusText();
	}

	@Override
	public String getContentType() {
		return response().getContentType();
	}

	@Override
	public String getHeader(String name) {
		return response().getHeader(name);
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return response().getHeaders();
	}

	@Override
	public Cookie getCookie(String cookieName) {
		for (com.ning.http.client.Cookie cookie : response.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				return NingTransformers.fromNingCookie.to(cookie);
			}
		}
		return null;
	}

	@Override
	public List<Cookie> getCookies() {
		return ETransformers.transformAllUsing(NingTransformers.fromNingCookie).to(response.getCookies());
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
		try {
			return response().getResponseBodyAsBytes();
		} catch (IOException e) {
			throw new HttpResponseException(e, "Failed to get response body: %s", e.getMessage());
		}
	}

	@Override
	public InputStream getBodyAsStream() {
		try {
			return response().getResponseBodyAsStream();
		} catch (IOException e) {
			throw new HttpResponseException(e, "Failed to get response body: %s", e.getMessage());
		}
	}

	@Override
	public URI getUri() {
		try {
			return response().getUri();
		} catch (MalformedURLException e) {
			throw new HttpResponseException(e, "Uri cannot be parsed: %s", e.getMessage());
		}
	}

	private Response response() {
		if (response == null) {
			try {
				response = future.get();
				return response;
			} catch (InterruptedException e) {
				throw new HttpRequestException("Failed to wait for completion of asynchronous request: %s", e.getMessage());
			} catch (ExecutionException e) {
				throw new HttpRequestException("Failed to get result for asynchronous request: %s", e.getMessage());
			}
		}
		return response;
	}
}
