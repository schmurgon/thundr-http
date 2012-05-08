package com.atomicleopard.webFramework.http.service;

import com.atomicleopard.webFramework.exception.BaseException;

public class HttpException extends BaseException {
	private static final long serialVersionUID = -7412818353654682030L;

	public HttpException(String format, Object... formatArgs) {
		super(format, formatArgs);
	}

	public HttpException(Throwable cause, String format, Object... formatArgs) {
		super(cause, format, formatArgs);
	}
}