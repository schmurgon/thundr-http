package com.threewks.thundr.http.service.typeTransformer;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;

public class OutgoingDefaultTypeTransformer implements ETransformer<Object, InputStream> {
	private static final OutgoingStringTypeTransformer stringTypeConvertor = new OutgoingStringTypeTransformer();

	@Override
	public InputStream to(Object from) {
		return stringTypeConvertor.to(from.toString());
	}

}
