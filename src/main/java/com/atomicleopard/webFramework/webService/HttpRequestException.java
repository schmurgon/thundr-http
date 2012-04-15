package com.atomicleopard.webFramework.webService;

import com.atomicleopard.webFramework.exception.BaseException;

public class HttpRequestException extends BaseException {
	private static final long serialVersionUID = 2788663509700705018L;

	public HttpRequestException(String format, Object... formatArgs) {
		super(format, formatArgs);
	}

	public HttpRequestException(Throwable cause, String format, Object... formatArgs) {
		super(cause, format, formatArgs);
	}
}
