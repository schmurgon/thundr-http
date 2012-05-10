package com.threewks.thundr.http.service.typeTransformer;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;

public class IncomingInputStreamTypeTransformer implements ETransformer<InputStream, InputStream> {
	@Override
	public InputStream to(InputStream from) {
		return from;
	}
}
