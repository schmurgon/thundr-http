package com.threewks.thundr.http.service;

public class HttpResponseException extends HttpException {
	private static final long serialVersionUID = 6430908639463941665L;

	public HttpResponseException(String format, Object... formatArgs) {
		super(format, formatArgs);
	}

	public HttpResponseException(Throwable cause, String format, Object... formatArgs) {
		super(cause, format, formatArgs);
	}

}
