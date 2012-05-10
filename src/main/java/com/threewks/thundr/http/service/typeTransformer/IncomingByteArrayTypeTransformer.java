package com.threewks.thundr.http.service.typeTransformer;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.util.Streams;

public class IncomingByteArrayTypeTransformer implements ETransformer<InputStream, byte[]> {
	@Override
	public byte[] to(InputStream from) {
		return Streams.readBytes(from);
	}
}
