package com.threewks.thundr.http.service.typeTransformer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.exception.BaseException;

public class OutgoingStringTypeTransformer implements ETransformer<String, InputStream> {
	private static final String encoding = "UTF-8";

	@Override
	public InputStream to(String from) {
		return new ByteArrayInputStream(toBytes(from));
	}

	public static byte[] toBytes(String string) {
		try {
			return string.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new BaseException(e, "Failed to convert string to bytes - this platform doesnt support %s: %s", encoding, e.getMessage());
		}
	}

}
