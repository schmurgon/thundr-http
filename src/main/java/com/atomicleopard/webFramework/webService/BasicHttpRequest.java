package com.atomicleopard.webFramework.webService;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jodd.util.Base64;
import jodd.util.StringUtil;

import com.atomicleopard.expressive.transform.ETransformers;
import com.atomicleopard.webFramework.http.Cookie;
import com.atomicleopard.webFramework.http.FileParameter;
import com.atomicleopard.webFramework.http.HttpSupport;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig;

public class BasicHttpRequest implements HttpRequest {
	private String encoding = "UTF-8";
	private String url;
	private String username;
	private String password;
	private Object body;
	private List<FileParameter> fileParameters;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	private List<Cookie> cookies;
	private String mimeType;
	private boolean followRedirects = true;
	private long timeout = 60000;
	private String scheme;
	private HttpService httpService;

	public BasicHttpRequest(HttpService httpService, String url) {
		this.httpService = httpService;
		this.url = HttpSupport.convertToValidUrl(url);
	}

	/**
	 * Add a MimeType to the web service request.
	 * 
	 * @param mimeType
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest mimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	/**
	 * define client authentication for a server host
	 * provided credentials will be used during the request
	 * 
	 * @param username
	 * @param password
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest authenticate(String username, String password, String scheme) {
		this.username = username;
		this.password = password;
		this.scheme = scheme;
		return this;
	}

	/**
	 * define client authentication for a server host
	 * provided credentials will be used during the request
	 * the basic scheme will be used
	 * 
	 * @param username
	 * @param password
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest authenticate(String username, String password) {
		return authenticate(username, password, "BASIC");
	}

	/**
	 * Indicate if the WS should continue when hitting a 301 or 302
	 * 
	 * @return the WebRequest for chaining.
	 */
	public HttpRequest followRedirects(boolean value) {
		this.followRedirects = value;
		return this;
	}

	public HttpRequest timeout(long millis) {
		this.timeout = millis;
		return this;
	}

	public HttpRequest files(FileParameter... fileParameters) {
		this.fileParameters = Arrays.asList(fileParameters);
		return this;
	}

	public HttpRequest files(List<FileParameter> fileParameters) {
		this.fileParameters = fileParameters;
		return this;
	}

	public HttpRequest body(Object body) {
		this.body = body;
		return this;
	}

	public HttpRequest headers(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public HttpRequest header(String name, String value) {
		this.headers.put(name, value);
		return this;
	}

	public HttpRequest cookie(Cookie cookie) {
		this.cookies.add(cookie);
		return this;
	}

	public HttpRequest cookies(Collection<Cookie> cookie) {
		this.cookies.addAll(cookie);
		return this;
	}

	public HttpRequest parameter(String name, String value) {
		this.parameters.put(name, value);
		return this;
	}

	public HttpRequest parameter(String name, Object value) {
		this.parameters.put(name, value);
		return this;
	}

	/**
	 * Adds the given parameters to request.
	 * GET: parameters are passed as a query string
	 * POST: parameters are passed in the body
	 * PUT: parameters are passed in the body
	 * DELETE: parameters are passed as a query string
	 */
	public HttpRequest parameters(Map<String, Object> parameters) {
		this.parameters.putAll(parameters);
		return this;
	}

	public HttpResponse get() {
		BoundRequestBuilder builder = createHttpClient().prepareGet(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addQueryParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create a GET request: %s", e.getMessage());
		}
	}

	public HttpResponse post() {
		BoundRequestBuilder builder = createHttpClient().preparePost(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create a POST request: %s", e.getMessage());
		}
	}

	public HttpResponse put() {
		BoundRequestBuilder builder = createHttpClient().preparePut(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create a PUT request: %s", e.getMessage());
		}
	}

	public HttpResponse delete() {
		BoundRequestBuilder builder = createHttpClient().prepareDelete(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addQueryParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create a DELETE request: %s", e.getMessage());
		}
	}

	public HttpResponse options() {
		BoundRequestBuilder builder = createHttpClient().prepareOptions(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addQueryParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create an OPTIONS request: %s", e.getMessage());
		}
	}

	public HttpResponse head() {
		BoundRequestBuilder builder = createHttpClient().prepareHead(getBaseUrl());
		setCommonProperties(builder);
		addHeaders(builder);
		addCookies(builder);
		addQueryParameters(builder);

		try {
			return new HttpResponseImpl(builder.execute());
		} catch (Exception e) {
			throw new HttpRequestException(e, "Failed to create a HEAD request: %s", e.getMessage());
		}
	}

	protected String basicAuthHeader() {
		return "Basic " + Base64.encodeToString(this.username + ":" + this.password);
	}

	protected String encode(String part) {
		try {
			return URLEncoder.encode(part, encoding);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String createQueryString() {
		StringBuilder sb = new StringBuilder();
		for (String key : this.parameters.keySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			Object value = this.parameters.get(key);

			if (value != null) {
				if (value instanceof Collection<?> || value.getClass().isArray()) {
					Collection<?> values = value.getClass().isArray() ? Arrays.asList((Object[]) value) : (Collection<?>) value;
					boolean first = true;
					for (Object v : values) {
						if (!first) {
							sb.append("&");
						}
						first = false;
						sb.append(encode(key)).append("=").append(encode(v.toString()));
					}
				} else {
					sb.append(encode(key)).append("=").append(encode(this.parameters.get(key).toString()));
				}
			}
		}
		return sb.toString();
	}

	private void setCommonProperties(BoundRequestBuilder builder) {
		builder.setFollowRedirects(followRedirects);
		builder.setBodyEncoding(encoding);
	}

	private void addCookies(BoundRequestBuilder builder) {
		List<com.ning.http.client.Cookie> cookies = ETransformers.transformAllUsing(NingTransformers.toNingCookie).to(this.cookies);
		for (com.ning.http.client.Cookie cookie : cookies) {
			builder.addCookie(cookie);
		}
	}

	private void addHeaders(BoundRequestBuilder builder) {
		for (Map.Entry<String, String> header : headers.entrySet()) {
			builder.addHeader(header.getKey(), StringUtil.toString(header.getValue()));
		}
	}

	private void addQueryParameters(BoundRequestBuilder builder) {
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			builder.addQueryParameter(parameter.getKey(), StringUtil.toString(parameter.getValue()));
		}
	}

	private void addParameters(BoundRequestBuilder builder) {
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			builder.addParameter(parameter.getKey(), StringUtil.toString(parameter.getValue()));
		}
	}

	private void addBody(BoundRequestBuilder builder) {
		InputStream is = httpService.convert(body);
		builder.setBody(is);
	}

	/**
	 * Return the request url without any request parameters
	 * 
	 * @return
	 */
	private String getBaseUrl() {
		return StringUtil.cutToIndexOf(url, '?');
	}

	private AsyncHttpClient createHttpClient() {
		AsyncHttpClientConfig.Builder config = new AsyncHttpClientConfig.Builder();
		config.setRequestTimeoutInMs((int) timeout);
		config.setConnectionTimeoutInMs((int) timeout);
		return new AsyncHttpClient(config.build());
	}
}
