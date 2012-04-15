package com.atomicleopard.webFramework.webService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomicleopard.expressive.transform.ETransformers;
import com.atomicleopard.webFramework.http.Cookie;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

/**
 * This is not thread safe.
 */
public class HttpResponseImpl implements HttpResponse {

	private ListenableFuture<Response> future;
	private Response response;

	public HttpResponseImpl(ListenableFuture<Response> listenableFuture) {
		this.future = listenableFuture;
	}

	public int getStatus() {
		return response().getStatusCode();
	}

	public String getStatusText() {
		return response().getStatusText();
	}

	public String getContentType() {
		return response().getContentType();
	}

	public String getHeader(String name) {
		return response().getHeader(name);
	}

	public List<Cookie> getCookies() {
		return ETransformers.transformAllUsing(NingTransformers.fromNingCookie).to(response.getCookies());
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
