package com.threewks.thundr.http.service.typeTransformer;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.util.Streams;

public class IncomingStringTypeTransformer implements ETransformer<InputStream, String> {
	@Override
	public String to(InputStream from) {
		return Streams.readString(from);
	}
}
